package Models;

import Components.Dir;
import Components.Position;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public abstract class Cell implements ICell {
    protected ICellType type;
    protected Color color;
    private boolean needsUpdate, stateChanged;
    protected Cell[] neighbours;
    protected boolean doColorUpdate = true;
    protected Position position;

    public Cell() {
        this.neighbours = new Cell[6];
    }

    public ICellType getType() {
        return this.type;
    }

    public void setType(ICellType type) {
        this.type = type;
        this.setStateChanged();
    }

    public void setPosition(Position p) {
        this.position = p;
    }

    public Position getPosition() {
        return position;
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
        this.color = this.getColor();
    }

    public void onTickBase(int epoch) {
        if (this.stateChanged) {
            this.stateChanged = false;
            return;
        }
        this.onTick(epoch);
    }

    public Cell getNeighbour(Dir d) {
        return this.neighbours[d.ordinal()];
    }
}
