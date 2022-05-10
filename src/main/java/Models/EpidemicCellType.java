package Models;

import javafx.scene.paint.Color;

public enum EpidemicCellType implements ICellType {
    DEFAULT,
    AIR,
    BRICK,
    WOOD,
    GLASS;

    public double getDensity() {
        switch (this) {
            case AIR, BRICK, WOOD, GLASS:
                return 0;
        }
        return 0;
    }

    public double getSepecifcHeat() {
        return 0;
    }

    public double getHeatGeneratingPower() {
        return 0;
    }

    public double getHeatTransferCoefficient() {
        return 0;
    }

}
