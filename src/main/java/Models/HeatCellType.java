package Models;
import Components.ICellType;
import javafx.scene.paint.Color;

public enum HeatCellType implements ICellType {
    DEFAULT,
    AIR,
    BRICK,
    WOOD,
    GLASS,
    HEATER,
    OUTSIDE;

    public double getDensity() {
        return switch (this) {
            case AIR, DEFAULT -> 1.2;
            case BRICK -> 1000;
            case WOOD -> 750;
            case GLASS -> 2500;
//            Assuming HEATER is made of steel
            case HEATER -> 7850;
            case OUTSIDE -> -1;
        };
    }


    /*public double getSpecificHeat() {
        return switch (this) {
            case AIR, DEFAULT -> 1;
            case BRICK -> 2;
            case WOOD -> 3;
            case GLASS -> 4;
//            Assuming HEATER is made of steel
            case HEATER -> 5;
            case OUTSIDE -> -1;
        };
    }*/

    public double getSpecificHeat() {
        return switch (this) {
            case AIR, DEFAULT -> 1005;
            case BRICK -> 900;
            case WOOD -> 2390;
            case GLASS -> 5200;
            case HEATER -> 5;
            case OUTSIDE -> -1;
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
//            Assuming HEATER is made of steel
            case HEATER -> 50;
            case OUTSIDE -> -1;
        };
    }

    public double getConvectionCoefficient() {
        return switch (this) {
            case AIR, DEFAULT -> 1.5;
            case BRICK, WOOD, GLASS -> 0;
//            Assuming HEATER is made of steel
            case HEATER -> 0;
            case OUTSIDE -> -1;
        };
    }

    public double getHeatGenerated() {
        return switch (this) {
            case BRICK, WOOD, GLASS, OUTSIDE, AIR, DEFAULT -> 0;
            case HEATER -> 1000;
        };
    }

    public boolean isSolid() {
        return switch (this) {
            case DEFAULT -> false;
            case AIR -> false;
            case BRICK -> true;
            case WOOD -> true;
            case GLASS -> true;
            case HEATER -> true;
            case OUTSIDE -> false;
        };
    }

    public double getVolume() {
        return 0.001; //m3
    }


    public double getArea() {
        return 0.1; //m from deinsty root
    }
    // TODO this is not a property of HeatCellType, right?
    //that is the fastest, otherwise weird shit is happening
    public double deltaTime() {
        return 150;
    }

    @Override
    public Color getColor() {
        return null;
    }
}
