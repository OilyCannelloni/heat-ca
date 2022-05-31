package Components;

import Models.HeatCell;
import Models.HeatCellType;

public abstract class Scenario {
    private final Class<? extends Cell> cellClass;

    public Scenario(Class<? extends Cell> cellClass) {
        this.cellClass = cellClass;
    }

    public Grid build(int width, int height) {
        return new Grid(width, height, cellClass);
    }

    public GridStack build(int width, int height, int depth) {
        GridStack gridStack = new GridStack();
        for (int z = 0; z < depth; z++) {
            gridStack.addConnectedLayer(new Grid(width, height, cellClass));
        }
        return gridStack;
    }

    public String printAction() {
        return "";
    }

    public void addObject (GridStack grid3d, int xb, int xe, int yb, int ye, int zb, int ze, HeatCellType type, int temp){
        for(int x = xb; x <= xe; x++){
            for (int y = yb; y <= ye; y++){
                for (int z = zb; z <= ze; z++){
                    ((HeatCell) grid3d.getCell(x, y, z)).setType(type);
                    ((HeatCell) grid3d.getCell(x,y,z)).setTemperature(temp);
                }
            }
        }
    }
}
