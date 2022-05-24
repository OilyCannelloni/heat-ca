package GUI;

import Components.*;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class DisplayGrid extends GridPane implements Iterable2DGrid<DisplayCell> {
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
    public int getGridWidth() {
        return width;
    }

    @Override
    public int getGridHeight() {
        return height;
    }

    public DisplayCell get(int x, int y) {
        return this.displayCells[x][y];
    }

    public DisplayCell get(Position position) {
        return this.displayCells[position.x][position.y];
    }

    public void updateAll() {
        Grid activeGrid = this.gridStack.getActiveGrid();
        for (Position position : this.positions()) {
            Cell cell = activeGrid.get(position);
            Color color = cell.getColor();
            this.get(position).setColor(color);
        }
    }
}
