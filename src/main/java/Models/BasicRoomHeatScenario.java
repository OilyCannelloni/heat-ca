package Models;

import Components.*;

public class BasicRoomHeatScenario extends Scenario{

    public BasicRoomHeatScenario() {
        super(HeatCell.class);
    }

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

        // TODO zrobić jakiś fajniejszy domek z większą liczbą grzejników i jakimiś rzeczami w środku
        // i np 2 warianty z dużymi oknami i małymi żeby było widać że duże okna = potrzeba mocniejszego grzejnika
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

        addObject(grid3d,20,30,25,30,3,4, HeatCellType.HEATER, 20);
        // TODO make this heater colder as it warms up to like 300+ degrees ruining the colors

        addObject(grid3d,10,50,5,19,1,1, HeatCellType.GLASS, 6);
        addObject(grid3d, 15, 45, width-3, 25, 5, 11, HeatCellType.WOOD, 20);

        return grid3d;
    }
}