package Models;

import Components.ColorGradient;
import GUI.Cell;
import javafx.scene.paint.Color;

public class HeatCell extends Cell {
    private double temperature;
    private static final ColorGradient gradient = new ColorGradient(
            Color.DARKBLUE,
            Color.WHITE,
            Color.GREEN,
            Color.YELLOW,
            Color.RED,
            Color.DARKRED,
            Color.BLACK
    );

    public HeatCell() {
        super();
        this.doColorUpdate = false;
        this.setType(HeatCellType.DEFAULT);
    }

    @Override
    public HeatCellType getType() {
        return (HeatCellType) this.type;
    }

    @Override
    public void onTick(int epoch) {
        // TODO
        // if (this.type = grzała) this.doGrzałaStuff()
        // else if (this.type = naZewnątrz) this.doNothing()
        // else doDefaultCase()...
        // TODO konwekcja

        HeatCellType myType = this.getType();
        double newTemperature = this.getTemperature();
        newTemperature += 1/(myType.getSpecificHeat() * myType.getDensity()) * heatExchange();
        this.setTemperature(newTemperature);
    }

    private double heatExchange() {
        HeatCellType type = this.getType();
        double heatTransferCoef = type.getHeatTransferCoefficient();
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
        double expectedMinTemp = 10;
        double expectedMaxTemp = 80;
        double w1 = (temperature - expectedMinTemp) / (expectedMaxTemp - expectedMinTemp);
        w1 = Math.min(Math.max(w1, 0), 1);
        return gradient.getByPercentage(w1);
    }
}
