package Models;

import GUI.CAGrid;

public class EpidemicScenario extends Scenario {
    public EpidemicScenario() {
        super(HeatCell.class);
    }

    @Override
    public CAGrid build(int width, int height) {
        CAGrid grid = super.build(width, height);
        for(int i=0; i<width; i++){
            grid.get(i,0).setType(HeatCellType.BRICK);
            grid.get(i,height-1).setType(HeatCellType.BRICK);
        }
        for(int i=0; i<height; i++){
            grid.get(0,i).setType(HeatCellType.BRICK);
            grid.get(width-1,i).setType(HeatCellType.BRICK);
        }
        return grid;
    }
}
