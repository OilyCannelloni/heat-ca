package Components;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Represents the grid in which the simulation takes place.
 * Only square grid, for now
 */

public class Grid extends Abstract2DGrid<Cell> {
    /**
     *  Attributes
     */
    private final Cell[][] cells;
    private int epoch = 0;

    /**
     * Creates a new grid
     * @param width - width of the grid in cells
     * @param height - height of the grid in cells
     * @param cellClass - A subclass of Cell, which defines the behavior of a cell
     *                  in a particular simulation. It should override onTick() and getColor()
     */
    public Grid(int width, int height, Class<? extends Cell> cellClass) {
        super(width, height);

        this.cells = new Cell[width][height];
        for (Position position : this.positions()) {
            // Create an instance of CellClass on every position
            try {
                this.cells[position.x][position.y] = cellClass.getConstructor().newInstance();
                this.cells[position.x][position.y].setPosition(position);
            } catch (
                    InvocationTargetException | InstantiationException
                     | IllegalAccessException | NoSuchMethodException e
            ) {
                throw new RuntimeException(e);
            }
        }
        this.applyNeighbourhood();
    }

    /**
     * Returns a cell at given position
     * @param x - x coordinate of position
     * @param y - y coordinate of position
     * @return - Cell object at (x, y)
     */
    public Cell get(int x, int y) {
        return this.cells[x][y];
    }

    public Cell get(Position position) {
        return this.cells[position.x][position.y];
    }

    /**
     * Calls the tick() method of every cell
     */
    public void tickAll() {
        this.shuffledElements().forEach(cell -> cell.onTickBase(epoch++));
    }

    /**
     * Binds the cells to each other
     */
    private void applyNeighbourhood() {
        for (int i = 1; i < this.width - 1; i++) {
            for (int j = 1; j < this.height - 1; j++) {
                Cell cell = this.get(i, j);
                cell.addNeighbour(Dir.DOWN, this.get(i, j+1));
                cell.addNeighbour(Dir.LEFT, this.get(i-1, j));
                cell.addNeighbour(Dir.UP, this.get(i, j-1));
                cell.addNeighbour(Dir.RIGHT, this.get(i+1, j));
            }
        }
    }
}
