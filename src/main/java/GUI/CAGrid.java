package GUI;

import Backend.Algorithm;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class CAGrid extends GridPane {
    private int CELL_SIZE = 20;
    private App app;
    private int width, height;
    private Cell[][] cells;

    public CAGrid(App app, int width, int height) {
        this.app = app;
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                this.cells[i][j] = new Cell();
                this.add(this.cells[i][j], i, j, 1, 1);
            }
        }

        this.setGridLinesVisible(true);

        this.setBackground(new Background(new BackgroundFill(
                Color.DARKGRAY,
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));

        this.setOpacity(0.6);
    }

    public void updateAll() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (Algorithm.random.nextDouble() < 0.1)
                    this.cells[i][j].setColor(Color.AQUA);
                else
                    this.cells[i][j].setColor(Color.WHEAT);
                this.cells[i][j].update();
            }
        }
    }
}
