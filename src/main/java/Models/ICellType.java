package Models;

import javafx.scene.paint.Color;

public interface ICellType {
    String toString();
    double getDensity();
    double getSepecifcHeat();
    double getHeatGeneratingPower();
    double getHeatTransferCoefficient();

}
