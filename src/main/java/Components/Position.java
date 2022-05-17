package Components;

public class Position {
    public int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Position other) {
        return this.x == other.x && this.y == other.y;
    }

}
