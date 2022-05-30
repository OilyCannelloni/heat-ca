package Components;

import GUI.Symbol;

import java.util.Collection;
import java.util.HashMap;

public abstract class Cell implements ICell {
    private ICellType type;
    private boolean stateChanged;
    private final HashMap<Dir, Cell> neighbours;
    private Position position;

    public Cell() {
        this.neighbours = new HashMap<>();
    }

    public ICellType getType() {
        return this.type;
    }

    public void setType(ICellType type) {
        this.type = type;
        this.setStateChanged();
    }

    public Symbol getSymbol() {
        return Symbol.EMPTY;
    }

    public void setPosition(Position p) {
        this.position = p;
    }

    public Position getPosition() {
        return position;
    }

    public void addNeighbour(Dir d, Cell cell) {
        this.neighbours.put(d, cell);
    }

    public Cell getNeighbour(Dir d) {
        return this.neighbours.get(d);
    }

    public Collection<Cell> getNeighbours() {
        return neighbours.values();
    }

    public void setStateChanged() {
        this.stateChanged = true;
    }

    public void onTickBase(int epoch) {
        if (this.stateChanged) {
            this.stateChanged = false;
            return;
        }
        this.onTick(epoch);
    }
}
