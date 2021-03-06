package Models;

import Components.*;

public class HeatScenario extends Scenario {
    public HeatScenario() {
        super(HeatCell.class);
    }

    @Override
    public Grid build(int width, int height) {
        Grid grid = super.build(width, height);

        double startingTemp = 20;
        grid.elements().forEach((Cell cell) -> {
            HeatCell heatCell = (HeatCell) cell;
            heatCell.setTemperature(startingTemp);
        });

        Position[] startingPositions = {new Position(10, 10), new Position(20, 20)};
        for (Position p : startingPositions) {
            HeatCell cell = (HeatCell) grid.get(p);
            cell.setTemperature(10000);
        }

        // example of const temperature block
        HeatCell cell = (HeatCell) grid.get(new Position(15, 15));
        cell.setType(HeatCellType.HEATER);

        return grid;
    }


    @Override
    public GridStack build(int width, int height, int depth) {
        GridStack grid3d = super.build(width, height, depth);

        grid3d.forEach(grid -> grid.elements().forEach(cell -> {
            HeatCell heatCell = (HeatCell) cell;
            heatCell.setTemperature(20);
        }));

        ((HeatCell) grid3d.getCell(10, 10, 3)).setTemperature(10000);

        return grid3d;
    }
}
