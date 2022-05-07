package Models;

import Backend.Algorithm;
import Components.Dir;
import GUI.Cell;

public class EpidemicCell extends Cell {
    private final static double
            RECOVERY_CHANCE = 0.04,
            DEATH_CHANCE = 0.005;

    private final static int
            IMMUNITY_LENGTH = 100;

    private int lastImmunizedEpoch;

    public EpidemicCell() {
        super();
        this.setType(EpidemicCellType.DEFAULT);
        this.lastImmunizedEpoch = 0;
    }

    @Override
    public void onTick(int epoch) {
        if (this.getType() == EpidemicCellType.INFECTED) {
            if (Algorithm.random.nextDouble() < RECOVERY_CHANCE) {
                this.setType(EpidemicCellType.IMMUNE);
                this.lastImmunizedEpoch = epoch;
                return;
            }

            if (Algorithm.random.nextDouble() < DEATH_CHANCE) {
                this.setType(EpidemicCellType.DEAD);
                return;
            }

            Dir d = Dir.random();
            EpidemicCell target = (EpidemicCell) this.getNeighbour(d);
            if (target.getType() == EpidemicCellType.DEFAULT)
                target.setType(EpidemicCellType.INFECTED);
            return;
        }

        if (this.getType() == EpidemicCellType.IMMUNE) {
            if (epoch - this.lastImmunizedEpoch > IMMUNITY_LENGTH){
                this.setType(EpidemicCellType.DEFAULT);
            }
        }
    }
}
