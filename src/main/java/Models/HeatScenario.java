package Models;

import Components.Position;
import GUI.Cell;
import GUI.Grid;

public class HeatScenario extends Scenario {
    public HeatScenario() {
        super(HeatCell.class);
    }

    @Override
    public Grid build(int width, int height) {
        Grid grid = super.build(width, height);

        double startingTemp = 20;
        grid.forEach((Cell cell) -> {
            HeatCell heatCell = (HeatCell) cell;
            heatCell.setTemperature(startingTemp);
        });

        Position[] startingPositions = {new Position(10, 10), new Position(20, 20)};
        for (Position p : startingPositions) {
            HeatCell cell = (HeatCell) grid.get(p);
            cell.setTemperature(10000);
        }

        return grid;
    }
}
