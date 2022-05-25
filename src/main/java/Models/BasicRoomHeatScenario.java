package Models;

import Components.*;

public class BasicRoomHeatScenario extends Scenario{

    public BasicRoomHeatScenario() {
        super(HeatCell.class);
    }

//    @Override
//    public Grid build(int width, int height) {
//        Grid grid = super.build(width, height);
//
//        double startingTemp = 20;
//        grid.elements().forEach((Cell cell) -> {
//            HeatCell heatCell = (HeatCell) cell;
//            heatCell.setTemperature(startingTemp);
//        });
//
//        Position[] startingPositions = {new Position(10, 10), new Position(20, 20)};
//        for (Position p : startingPositions) {
//            HeatCell cell = (HeatCell) grid.get(p);
//            cell.setTemperature(10000);
//        }
//
//        // example of const temperature block
//        HeatCell cell = (HeatCell) grid.get(new Position(15, 15));
//        cell.setType(HeatCellType.HEATER);
//
//        return grid;
//    }

    private void addObject (GridStack grid3d, int xb, int xe, int yb, int ye, int zb, int ze, HeatCellType type, int temp){
        for(int x = xb; x <= xe; x++){
            for (int y = yb; y <= ye; y++){
                for (int z = zb; z <= ze; z++){
                    ((HeatCell) grid3d.getCell(x, y, z)).setType(type);
                    ((HeatCell) grid3d.getCell(x,y,z)).setTemperature(temp);
                }
            }
        }
    }
    @Override
    public GridStack build(int width, int height, int depth) {
        GridStack grid3d = super.build(width, height, depth);

        grid3d.forEach(grid -> grid.elements().forEach(cell -> {
            HeatCell heatCell = (HeatCell) cell;
            heatCell.setTemperature(20);
        }));

//        Adding outsides
        addObject(grid3d, 0,0, 0,height-1, 0, depth-1, HeatCellType.OUTSIDE, 0);
        addObject(grid3d, width-1,width-1, 0,height-1, 0, depth-1, HeatCellType.OUTSIDE, 0);
        addObject(grid3d, 0,width-1, 0,0, 0, depth-1, HeatCellType.OUTSIDE, 0);
        addObject(grid3d, 0,width-1, height-1,height-1, 0, depth-1, HeatCellType.OUTSIDE, 0);
        addObject(grid3d, 0,width-1, 0,height-1, 0, 0, HeatCellType.OUTSIDE, 0);
        addObject(grid3d, 0,width-1, 0,height-1, depth-1, depth-1, HeatCellType.OUTSIDE, 0);

//        Adding brick walls
        addObject(grid3d, 1,1, 1,height-2, 1, depth-2, HeatCellType.BRICK, 10);
        addObject(grid3d, width-2,width-2, 1,height-2, 1, depth-2, HeatCellType.BRICK, 10);
        addObject(grid3d, 1,width-2, 1,1, 1, depth-2, HeatCellType.BRICK, 10);
        addObject(grid3d, 1,width-2, height-2,height-2, 1, depth-2, HeatCellType.BRICK, 10);
        addObject(grid3d, 1,width-2, 1,height-2, 1, 1, HeatCellType.BRICK, 10);
        addObject(grid3d, 1,width-2, 1,height-2, depth-2, depth-2, HeatCellType.BRICK, 10);

        addObject(grid3d,10,50,20,25,3,4, HeatCellType.HEATER, 20);
        addObject(grid3d,10,50,5,19,1,1, HeatCellType.GLASS, 6);
        addObject(grid3d, 15, 45, width-3, 25, 5, 11, HeatCellType.WOOD, 20);

        return grid3d;
    }

}