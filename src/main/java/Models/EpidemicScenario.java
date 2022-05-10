package Models;

import GUI.CAGrid;

public class EpidemicScenario extends Scenario {
    public EpidemicScenario() {
        super(HeatCell.class);
    }

    @Override
    public CAGrid build(int width, int height) {
        CAGrid grid = super.build(width, height);
        grid.get(26, 15).setType(HeatCellType.INFECTED);
        grid.get(4, 6).setType(HeatCellType.INFECTED);
        return grid;
    }
}
