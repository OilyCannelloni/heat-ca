package GUI;


import Models.Grid;
import Models.GridStack;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.Iterator;

public class DisplayGrid extends GridPane implements Iterable<DisplayCell> {
    class DisplayGridIterator implements Iterator<DisplayCell> {
        private int x, y;

        @Override
        public boolean hasNext() {
            return y * width + x < size();
        }

        @Override
        public DisplayCell next() {
            DisplayCell ret = get(x, y);
            if (++x == width) {
                x = 0;
                y++;
            }
            return ret;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove a DisplayCell from DisplayGrid!");
        }
    }


    private final int width, height;
    private final DisplayCell[][] displayCells;
    public GridStack gridStack;

    public DisplayGrid(int width, int height) {
        this.width = width;
        this.height = height;
        this.displayCells = new DisplayCell[width][height];
        this.gridStack = new GridStack();

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                this.displayCells[i][j] = new DisplayCell();
                this.add(this.displayCells[i][j], i, j, 1, 1);
            }
        }

        this.setGridLinesVisible(true);
        this.setBackground(new Background(new BackgroundFill(
                Color.DARKGRAY,
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));
    }

    @Override
    public Iterator<DisplayCell> iterator() {
        return new DisplayGridIterator();
    }

    public int size() {
        return width * height;
    }

    public DisplayCell get(int x, int y) {
        return this.displayCells[x][y];
    }

    public void updateAll() {
        Grid activeGrid = this.gridStack.getActiveGrid();
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                Color color = activeGrid.get(i, j).getColor();
                this.get(i, j).setColor(color);
            }
        }
    }
}
