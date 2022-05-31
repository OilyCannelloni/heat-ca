package Models;

import Components.*;

import java.util.concurrent.atomic.AtomicReference;

public class BasicRoomHeatScenario extends Scenario{
    private GridStack grid3d;
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
    public String printAction() {
        AtomicReference<Double> sum = new AtomicReference<>(0.0);
        AtomicReference<Integer> count = new AtomicReference<>(0);
        grid3d.forEach(grid -> grid.elements().forEach(
                cell -> {
                    HeatCell heatCell = (HeatCell) cell;
                    if (heatCell.getType() == HeatCellType.OUTSIDE || heatCell.getType() == HeatCellType.HEATER) return;
                    sum.updateAndGet(x -> x + cell.getValue());
                    count.updateAndGet(x -> x + 1);
                }
        ));

        return String.format("Average temp: %.2f", sum.get() / count.get());
    }

    @Override
    public GridStack build(int width, int height, int depth) {
        grid3d = super.build(width, height, depth);

        // TODO zrobić jakiś fajniejszy domek z większą liczbą grzejników i jakimiś rzeczami w środku
        // i np 2 warianty z dużymi oknami i małymi żeby było widać że duże okna = potrzeba mocniejszego grzejnika
        grid3d.forEach(grid -> grid.elements().forEach(cell -> {
            HeatCell heatCell = (HeatCell) cell;
            heatCell.setTemperature(20);
        }));

        // adding outside
        addObject(grid3d, 0,0, 0,height-1, 0, depth-1, HeatCellType.OUTSIDE, 0);
        addObject(grid3d, width-1,width-1, 0,height-1, 0, depth-1, HeatCellType.OUTSIDE, 0);
        addObject(grid3d, 0,width-1, 0,0, 0, depth-1, HeatCellType.OUTSIDE, 0);
        addObject(grid3d, 0,width-1, height-1,height-1, 0, depth-1, HeatCellType.OUTSIDE, 0);
        addObject(grid3d, 0,width-1, 0,height-1, 0, 0, HeatCellType.OUTSIDE, 0);
        addObject(grid3d, 0,width-1, 0,height-1, depth-1, depth-1, HeatCellType.OUTSIDE, 0);

//        Adding brick walls
        addObject(grid3d, 1,1, 1,height-2, 1, depth-2, HeatCellType.ISOLATED_WALL, 10);
        addObject(grid3d, width-2,width-2, 1,height-2, 1, depth-2, HeatCellType.ISOLATED_WALL, 10);
        addObject(grid3d, 1,width-2, 1,1, 1, depth-2, HeatCellType.ISOLATED_WALL, 10);
        addObject(grid3d, 1,width-2, height-2,height-2, 1, depth-2, HeatCellType.ISOLATED_WALL, 10);
        addObject(grid3d, 1,width-2, 1,height-2, 1, 1, HeatCellType.ISOLATED_WALL, 10);
        addObject(grid3d, 1,width-2, 1,height-2, depth-2, depth-2, HeatCellType.ISOLATED_WALL, 10);

        // adding table
        addObject(grid3d, 2,12, 13,13, 2, 5, HeatCellType.WOOD, 20);
        addObject(grid3d, 2,2, 13,17, 2, 2, HeatCellType.WOOD, 20);
        addObject(grid3d, 12,12, 13,17, 2, 2, HeatCellType.WOOD, 20);
        addObject(grid3d, 12,12, 13,17, 5, 5, HeatCellType.WOOD, 20);
        addObject(grid3d, 2,4, 13,13, 6, 7, HeatCellType.WOOD, 20);
        addObject(grid3d, 2,2, 13,17, 7, 7, HeatCellType.WOOD, 20);
        addObject(grid3d, 4,4, 13,17, 7, 7, HeatCellType.WOOD, 20);

        // adding shelfs
        addObject(grid3d, 18,27, 8,17, 2, 3, HeatCellType.WOOD, 20);
        addObject(grid3d, 16,17, 8,17, 2, 9, HeatCellType.WOOD, 20);

        // adding heater
        addObject(grid3d, 27,27, 11,15, 6, 11, HeatCellType.HEATER, 100);

        // adding window
        addObject(grid3d, 1,1, 7,11, 3, 11, HeatCellType.GLASS, 20);

        // adding doors
        addObject(grid3d, 10,19, 5,17, 13, 13, HeatCellType.WOOD, 20);
        return grid3d;
    }
}