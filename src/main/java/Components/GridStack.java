package Components;

import java.util.ArrayList;

public class GridStack extends ArrayList<Grid> {
    private int activeIndex = 0;

    public GridStack() {}

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public Grid getActiveGrid() {
        return this.get(this.getActiveIndex());
    }

    public void addConnectedLayer(Grid grid) {
        this.add(grid);
        if (this.size() == 1) return;   // no connections if first layer

        Grid lastGrid = this.get(this.size() - 1);
        Grid oldLastGrid = this.get(this.size() - 2);

        oldLastGrid.elements().forEach(cell -> cell.addNeighbour(Dir.BACK, lastGrid.get(cell.getPosition())));
        lastGrid.elements().forEach(cell -> cell.addNeighbour(Dir.FRONT, oldLastGrid.get(cell.getPosition())));
    }

    public void tickAll() {
        this.forEach(Grid::tickAll);
    }

    public Cell getCell(int x, int y, int z) {
        return this.get(z).get(x, y);
    }
}
