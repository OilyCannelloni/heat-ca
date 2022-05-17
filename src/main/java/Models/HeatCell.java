package Models;

import GUI.Cell;
import javafx.scene.paint.Color;

public class HeatCell extends Cell {
    private double temperature;

    public HeatCell() {
        super();
        this.doColorUpdate = false;
        this.setType(HeatCellType.DEFAULT);
    }

    @Override
    public void onTick(int epoch) {
        ICellType myType = this.getType();
        double newTemperature = this.getTemperature();
        newTemperature += 1/(myType.getSpecificHeat() * myType.getDensity()) * heatExchange();
        this.setTemperature(newTemperature);
    }

    private double heatExchange() {
        double heatTransferCoef = this.getType().getHeatTransferCoefficient();
        double heatBalance = 0;
        double h = 1;
        for(Cell n : this.neighbours) {
            HeatCell neighbour = (HeatCell) n;
            double delta_T = neighbour.getTemperature() - this.getTemperature();
            heatBalance += (heatTransferCoef * delta_T) / (h * h);
        }
        return heatBalance;
    }

    public double getTemperature() { return this.temperature;}

    public void setTemperature(double newTemp) {
        this.temperature = newTemp;
    }

    public Color getColor() {
        double curTemp = this.getTemperature();
        if(curTemp >= 96) {
            return Color.rgb(255,13,13);
        }
        if(90 <= curTemp) {
            return Color.rgb(228,77,32);
        }
        if(70 <= curTemp) {
            return Color.rgb(228,143,32);
        }
        if(50 <= curTemp) {
            return Color.rgb(228,182,33);
        }
        if(30 <= curTemp) {
            return Color.rgb(157,228,33);
        }
        if(20 <= curTemp) {
            return Color.rgb(88,228,33);
        }
        if(10 <= curTemp) {
            return Color.rgb(33,228,173);
        }
        if(3 <= curTemp) {
            return Color.rgb(33,212,228);
        }
        if(0.5 <= curTemp) {
            return Color.rgb(33, 118, 228);
        }
        else {
            return Color.rgb(33,43,228);
        }
    }
}
