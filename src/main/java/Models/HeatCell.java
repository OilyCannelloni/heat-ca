package Models;

import Components.Cell;
import Components.ColorGradient;
import Components.Dir;
import GUI.Symbol;
import javafx.scene.paint.Color;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class HeatCell extends Cell {
    private final static boolean doConvection = true;
    private double temperature;
    private static double heaterMaxTemp = 70;

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
        /*if(!myType.isSolid())
            System.out.println("TAK");*/
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
        //I know that here for would be good but its 6am
        // Best done with directional property - but no time for that
        HeatCell downNei = (HeatCell) this.getNeighbour(Dir.DOWN);
        HeatCell upNei = (HeatCell) this.getNeighbour(Dir.UP);
        HeatCell leftNei = (HeatCell) this.getNeighbour(Dir.LEFT);
        HeatCell rightNei = (HeatCell) this.getNeighbour(Dir.RIGHT);
        HeatCell frontNei = (HeatCell) this.getNeighbour(Dir.FRONT);
        HeatCell backNei = (HeatCell) this.getNeighbour(Dir.BACK);
        HeatCellType myType = this.getType();
        double multiplicationConstant = myType.getArea() * myType.getConvectionCoefficient();
        double convectionExchange = 0;
        if (upNei != null && !upNei.getType().isSolid()) {
            convectionExchange += min(0, upNei.getTemperature() - this.getTemperature()) * multiplicationConstant;
        }

        if (downNei != null && !downNei.getType().isSolid()) {
            convectionExchange += max(0, downNei.getTemperature() - this.getTemperature()) * multiplicationConstant;
        }

        if (frontNei != null && !frontNei.getType().isSolid()) {
            convectionExchange += 0.1 * (frontNei.getTemperature() - this.getTemperature()) * multiplicationConstant;
        }

        if (backNei != null && !backNei.getType().isSolid()) {
            convectionExchange += 0.1 * (backNei.getTemperature() - this.getTemperature()) * multiplicationConstant;
        }

        if (leftNei != null && !leftNei.getType().isSolid()) {
            convectionExchange += 0.1 * (leftNei.getTemperature() - this.getTemperature()) * multiplicationConstant;
        }

        if (rightNei != null && !rightNei.getType().isSolid()) {
            convectionExchange += 0.1 * (rightNei.getTemperature() - this.getTemperature()) * multiplicationConstant;
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
}
