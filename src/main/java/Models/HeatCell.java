package Models;

import GUI.Cell;

public class HeatCell extends Cell {
    public HeatCell() {
        super();
        this.setType(HeatCellType.DEFAULT);
        this.lastImmunizedEpoch = 0;
    }

    @Override
    public void onTick(int epoch) {

    }
}
