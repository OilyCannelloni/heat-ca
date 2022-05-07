package Models;

import GUI.CAGrid;
import GUI.Cell;

public abstract class Scenario {
    private final Class<? extends Cell> cellClass;

    public Scenario(Class<? extends Cell> cellClass) {
        this.cellClass = cellClass;
    }

    public CAGrid build(int width, int height) {
        return new CAGrid(width, height, cellClass);
    }
}
