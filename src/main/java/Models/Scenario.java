package Models;

public abstract class Scenario {
    private final Class<? extends Cell> cellClass;

    public Scenario(Class<? extends Cell> cellClass) {
        this.cellClass = cellClass;
    }

    public Grid build(int width, int height) {
        return new Grid(width, height, cellClass);
    }
}
