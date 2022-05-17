package Models;
import javafx.scene.paint.Color;

public enum HeatCellType implements ICellType {
    DEFAULT,
    AIR,
    BRICK,
    WOOD,
    GLASS;

    // TODO add grzała & naZewnątrzNieZmieniająceTempCoś

    public double getDensity() {
        return switch (this) {
            case AIR, DEFAULT -> 1.2;
            case BRICK -> 1000;
            case WOOD -> 750;
            case GLASS -> 2500;
        };
    }


    public double getSpecificHeat() {
        return switch (this) {
            case AIR, DEFAULT -> 1;
            case BRICK -> 2;
            case WOOD -> 3;
            case GLASS -> 4;
        };
    }

    public double getHeatGeneratingPower() {
        return 0;
    }

    public double getHeatTransferCoefficient() {
        return switch (this) {
            case AIR, DEFAULT -> 0.025;
            case BRICK -> 0.57;
            case WOOD -> 0.32;
            case GLASS -> 1.1;
        };
    }

    @Override
    public Color getColor() {
        return null;
    }
}
