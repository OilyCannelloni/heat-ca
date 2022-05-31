package Components;

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
}
