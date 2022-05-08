package GUI;

import Components.Dir;
import Models.ICellType;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Cell extends StackPane {
    private ICellType type;
    private Color color;
    private boolean needsUpdate, stateChanged;
    private Cell[] neighbours;

    public Cell() {
        this.neighbours = new Cell[4];
        this.setColor(Color.WHITESMOKE);
        this.setOpacity(1.0);
        this.setPrefSize(15, 15);
    }

    public ICellType getType() {
        return this.type;
    }

    public void setType(ICellType type) {
        this.type = type;
        this.setStateChanged();
    }

    public void addNeighbour(Dir d, Cell cell) {
        this.neighbours[d.ordinal()] = cell;
    }

    public void setColor(Color color) {
        this.color = color;
        this.needsUpdate = true;
    }

    public void setUpdate() {
        this.needsUpdate = true;
    }

    public void setStateChanged() {
        this.stateChanged = true;
        this.needsUpdate = true;
    }

    private void clearFlags() {
        this.needsUpdate = false;
        this.stateChanged = false;
    }

    public boolean needsUpdate() {
        return this.needsUpdate;
    }

    public void beforeUpdate() {
        this.setColor(this.type.getColor());
    }

    public void update() {
        this.beforeUpdate();
        this.setBackground(new Background(new BackgroundFill(
                this.color,
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));
    }

    public void onTickBase(int epoch) {
        if (this.stateChanged) {
            this.stateChanged = false;
            return;
        }
        this.onTick(epoch);
    }

    public void onTick(int epoch) {

    }

    public Cell getNeighbour(Dir d) {
        return this.neighbours[d.ordinal()];
    }
}
