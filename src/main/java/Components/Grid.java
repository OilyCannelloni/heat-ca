package Components;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

/**
 * Represents the grid in which the simulation takes place.
 * Only square grid, for now
 */

public class Grid implements Iterable<Cell> {
    /**
     * Iteration mechanics, so that functions like forEach()
     * could be easily invoked without a million for loops
     */
    class GridIterator implements Iterator<Cell> {
        private int x, y;

        @Override
        public boolean hasNext() {
            return y * width + x < size();
        }

        @Override
        public Cell next() {
            Cell ret = get(x, y);
            if (++x == width) {
                x = 0;
                y++;
            }
            return ret;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove a cell from grid! Consider changing its type");
        }
    }

    public int size() {
        return width * height;
    }

    @Override
    public Iterator<Cell> iterator() {
        return new GridIterator();
    }

    /**
     *  Attributes
     */
    private final int width, height;
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
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {

                // Create an instance of cellClass in each field
                try {
                    this.cells[i][j] = cellClass.getConstructor().newInstance();
                    this.cells[i][j].setPosition(new Position(i, j));
                } catch (
                        InstantiationException | IllegalAccessException
                        | InvocationTargetException | NoSuchMethodException e
                ) {
                    e.printStackTrace();
                }

            }
        }

        this.applyMooreNeighbourhood();
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
     * TODO implement random order of updates
     */
    public void tickAll() {
        for (int i = 1; i < this.width - 1; i++) {
            for (int j = 1; j < this.height - 1; j++) {
                this.cells[i][j].onTickBase(epoch);
            }
        }
        this.epoch++;
    }

    /**
     * Binds the cells to each other
     */
    private void applyMooreNeighbourhood() {
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
