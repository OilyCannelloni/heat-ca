package GUI;

import Backend.Algorithm;
import Components.Dir;
import Components.Position;
import Epidemic.EpidemicCell;
import Epidemic.EpidemicCellType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class CAGrid extends GridPane {
    private int CELL_SIZE = 20;
    private App app;
    private int width, height;
    private Cell[][] cells;
    private LinkedList<Position> updatedPositions;
    private int epoch = 0;

    public CAGrid(App app, int width, int height) {
        this.app = app;
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];
        this.updatedPositions = new LinkedList<>();

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                this.cells[i][j] = new EpidemicCell();
                this.add(this.cells[i][j], i, j, 1, 1);
            }
        }

        EpidemicCell cell = (EpidemicCell) this.get(20, 10);
        cell.setType(EpidemicCellType.INFECTED);

        this.setGridLinesVisible(true);

        this.setBackground(new Background(new BackgroundFill(
                Color.DARKGRAY,
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));

        this.applyMooreNeighbourhood();
    }

    public void updateAll() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                this.cells[i][j].update();
            }
        }
    }

    public void updateWhereNeeded() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (this.cells[i][j].needsUpdate())
                    this.cells[i][j].update();
            }
        }
    }

    public Cell get(int x, int y) {
        return this.cells[x][y];
    }

    public Cell get(Position position) {
        return this.cells[position.x][position.y];
    }

    public void tickAll() {
        for (int i = 1; i < this.width - 1; i++) {
            for (int j = 1; j < this.height - 1; j++) {
                this.cells[i][j].onTickBase(epoch);
            }
        }
        this.epoch++;
    }

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
