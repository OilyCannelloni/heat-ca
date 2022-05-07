package Epidemic;

import Backend.Algorithm;
import Components.Dir;
import GUI.Cell;

public class EpidemicCell extends Cell {
    private final static double
            RECOVERY_CHANCE = 0.04,
            DEATH_CHANCE = 0.005;

    private final static int
            IMMUNITY_LENGTH = 100;

    private EpidemicCellType type;
    private int lastImmunizedEpoch;

    public EpidemicCell() {
        super();
        this.type = EpidemicCellType.DEFAULT;
        this.lastImmunizedEpoch = 0;
    }

    public void setType(EpidemicCellType type) {
        this.type = type;
        this.setStateChanged();
    }

    @Override
    public void beforeUpdate() {
        this.setColor(this.type.getColor());
    }

    @Override
    public void onTick(int epoch) {
        if (this.type == EpidemicCellType.INFECTED) {
            if (Algorithm.random.nextDouble() < RECOVERY_CHANCE) {
                this.setType(EpidemicCellType.IMMUNE);
                this.lastImmunizedEpoch = epoch;
                this.setUpdate();
                return;
            }

            if (Algorithm.random.nextDouble() < DEATH_CHANCE) {
                this.setType(EpidemicCellType.DEAD);
                this.setUpdate();
                return;
            }

            Dir d = Dir.random();
            EpidemicCell target = (EpidemicCell) this.getNeighbour(d);
            if (target.type == EpidemicCellType.DEFAULT)
                target.setType(EpidemicCellType.INFECTED);
            this.setUpdate();
            return;
        }

        if (this.type == EpidemicCellType.IMMUNE) {
            if (epoch - this.lastImmunizedEpoch > IMMUNITY_LENGTH){
                this.setType(EpidemicCellType.DEFAULT);
                this.setUpdate();
            }
        }
    }
}
