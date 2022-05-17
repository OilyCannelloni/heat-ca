package Models;

import javafx.scene.paint.Color;

public interface ICellType {
    String toString();
    double getDensity();
    double getSpecificHeat();
    double getHeatGeneratingPower();
    double getHeatTransferCoefficient();
    Color getColor();
}
