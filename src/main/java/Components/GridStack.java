package Components;

import Models.HeatCell;
import Models.HeatCellType;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

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

    public void tickAllNoShuffle() {
        this.forEach(Grid::tickAllNoShuffle);
        this.equalize();
    }

    public void equalize() {
        AtomicReference<Double> sum = new AtomicReference<>(0.0);
        AtomicReference<Integer> count = new AtomicReference<>(0);
        this.forEach(grid -> grid.elements().forEach(
                cell -> {
                    HeatCell heatCell = (HeatCell) cell;
                    if (heatCell.getType() != HeatCellType.AIR) return;
                    sum.updateAndGet(x -> x + cell.getValue());
                    count.updateAndGet(x -> x + 1);
                }
        ));

        double average = sum.get() / count.get();

        this.forEach(grid -> grid.elements().forEach(
                cell -> {
                    if (cell.getType() != HeatCellType.AIR) return;
                    HeatCell hc = (HeatCell) cell;
                    double deltaT = hc.getTemperature() - average;
                    hc.setTemperature(hc.getTemperature() - deltaT * 0.05);
                }
        ));
    }


    public Cell getCell(int x, int y, int z) {
        return this.get(z).get(x, y);
    }


}
