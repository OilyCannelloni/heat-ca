package Models;

import Components.Cell;
import Components.ColorGradient;
import Components.Dir;
import GUI.Symbol;
import javafx.scene.paint.Color;

import static java.lang.Math.max;

public class HeatCell extends Cell {
    private final static boolean doConvection = false;
    private double temperature;

    private static final ColorGradient gradient = new ColorGradient(
            Color.DARKBLUE,
            Color.WHITE,
            Color.GREEN,
            Color.YELLOW,
            Color.RED,
            Color.DARKRED
    );

    public HeatCell() {
        super();
        this.setType(HeatCellType.DEFAULT);
    }

    @Override
    public HeatCellType getType() {
        return (HeatCellType) super.getType();
    }

    @Override
    public void onTick(int epoch) {
        HeatCellType myType = this.getType();
        if (myType == HeatCellType.OUTSIDE) {
            return;
        }
        // TODO ???
//        if(myType == HeatCellType.HEATER){
//            double newTemperature = this.getTemperature();
//            newTemperature += myType.getHeatGenerated();
//            this.setTemperature(newTemperature);
//            return;
//        }
        double convectionHeatExchange = doConvection ? convection() : 0;
        double newTemperature = this.getTemperature();
        newTemperature += myType.deltaTime() / (myType.getSpecificHeat() * myType.getDensity()) * heatExchange();
        newTemperature += convectionHeatExchange;
        newTemperature += myType.getHeatGenerated();
        this.setTemperature(newTemperature);
    }

    /*
    TODO fix this!!!!!
    it should spread the air diagonally up too
    for example 0.6 of the convection going directly up and
    0.15 up-left, 0.15 up-right, 0.05 left and 0.05 right
    or weight these ratios somehow based on how much heat can go up
    do some testing and playing around
     */
    private double convection() {
        HeatCell upNei = (HeatCell) this.getNeighbour(Dir.UP);
        HeatCell leftNei = (HeatCell) this.getNeighbour(Dir.LEFT);
        HeatCell rightNei = (HeatCell) this.getNeighbour(Dir.RIGHT);
        HeatCellType myType = this.getType();
        double multiplicationConstant = myType.getArea() * myType.getConvectionCoefficient();
        double convectionExchange = 0;
        if (upNei != null) {
            convectionExchange += max(0, this.getTemperature() - upNei.getTemperature()) * multiplicationConstant;
        }

        if (leftNei != null) {
            convectionExchange += max(0, this.getTemperature() - leftNei.getTemperature()) * multiplicationConstant;
        }

        if (rightNei != null) {
            convectionExchange += max(0, this.getTemperature() - rightNei.getTemperature()) * multiplicationConstant;
        }
        return convectionExchange;
    }

    private double heatExchange() {
        HeatCellType type = this.getType();
        double heatTransferCoef = type.getHeatTransferCoefficient();
        double heatBalance = 0;
        // TODO fix this h as the simulation is going slowly as fck
        double cellLength = 1;
        for (Cell n : this.getNeighbours()) {
            if (n == null) continue;
            HeatCell neighbour = (HeatCell) n;
            double delta_T = neighbour.getTemperature() - this.getTemperature();
            heatBalance += (heatTransferCoef * delta_T) / (cellLength * cellLength);
        }
        return heatBalance;
    }

    public double getTemperature() {
        return this.temperature;
    }

    public void setTemperature(double newTemp) {
        this.temperature = newTemp;
    }

    @Override
    public Color getColor() {
        double expectedMinTemp = 10;
        double expectedMaxTemp = 80;
        double w1 = (temperature - expectedMinTemp) / (expectedMaxTemp - expectedMinTemp);
        w1 = Math.min(max(w1, 0), 1);
        return gradient.getByPercentage(w1);
    }

    @Override
    public Symbol getSymbol() {
        HeatCellType type = this.getType();
        if (type == HeatCellType.GLASS) return Symbol.DOTS;
        if (type == HeatCellType.HEATER) return Symbol.CIRCLE_WITH_DOT;
        if (type.isSolid()) return Symbol.CROSS;
        return Symbol.EMPTY;
    }
}
