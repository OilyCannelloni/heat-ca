package Models;

import java.awt.*;
import Backend.Algorithm;
import javafx.scene.paint.Color;
public enum HeatCellType implements ICellType {
    DEFAULT,
    AIR,
    BRICK,
    WOOD,
    GLASS;

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
            case AIR, DEFAULT -> 1008;
            case BRICK -> 850;
            case WOOD -> 1500;
            case GLASS -> 745;
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
}
