package Models;

import Components.Cell;
import Components.ColorGradient;
import Components.Dir;
import GUI.Symbol;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class HeatCell extends Cell {
    private final static boolean doConvection = true;
    private double temperature;
    private static double heaterMaxTemp = 100;

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

        double convectionHeatExchange = (doConvection && !myType.isSolid()) ? convection() : 0;
        double newTemperature = this.getTemperature();
        newTemperature += myType.deltaTime() / (myType.getSpecificHeat() * myType.getDensity()) * heatExchange();
        newTemperature += convectionHeatExchange;
        newTemperature += myType.getHeatGenerated();

        if (this.getType() == HeatCellType.HEATER)
            newTemperature = heaterMaxTemp;

        this.setTemperature(newTemperature);
    }

    private double convection() {
        HeatCellType myType = this.getType();
        double convectionExchange = 0;
        double multiplicationConstant = myType.getArea() * myType.getConvectionCoefficient();

        for(Map.Entry<Dir, Cell> neighbour : this.getNeighbourMap().entrySet()) {
            HeatCell curNeighbour = (HeatCell) neighbour.getValue();
            double delta_T = curNeighbour.getTemperature() - this.getTemperature();
            if (!curNeighbour.getType().isSolid()) {
                switch (neighbour.getKey()) {
                    case DOWN -> convectionExchange += max(0, delta_T) * multiplicationConstant;
                    case UP -> convectionExchange += min(0, delta_T) * multiplicationConstant;
                    case FRONT, BACK, LEFT, RIGHT -> convectionExchange += 0.1 * delta_T * multiplicationConstant;
                }
            }
        }
        return convectionExchange;
    }

    private double heatExchange() {
        HeatCellType type = this.getType();
        double heatTransferCoef = type.getHeatTransferCoefficient();
        double heatBalance = 0;
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
        w1 = min(max(w1, 0), 1);
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

    @Override
    public double getValue() {
        return temperature;
    }
}
