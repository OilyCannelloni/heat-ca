package Models;

import javafx.scene.paint.Color;

public enum EpidemicCellType implements ICellType {
    DEFAULT,
    INFECTED,
    IMMUNE,
    DEAD,
    VACCINATED;

    public Color getColor() {
        switch (this) {
            case DEFAULT:
                return Color.LIGHTGRAY;
            case INFECTED:
                return Color.FIREBRICK;
            case IMMUNE:
                return Color.CYAN;
            case DEAD:
                return Color.BLACK;
            case VACCINATED:
                return Color.GREENYELLOW;
            default:
                return Color.WHITE;
        }
    }
}
