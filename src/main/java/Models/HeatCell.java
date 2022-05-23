package Models;

import Components.ColorGradient;
import Components.Dir;
import javafx.scene.paint.Color;

public class HeatCell extends Cell {
    private final static boolean doConvection = false;

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
        HeatCellType myType = this.getType();
        if(myType == HeatCellType.HEATER || myType == HeatCellType.OUTSIDE){
            return;
        }
        double conventionHeatExchange = doConvection ? convection() : 0;
        double newTemperature = this.getTemperature();
        newTemperature += 1/(myType.getSpecificHeat() * myType.getDensity()) * heatExchange();
        this.setTemperature(newTemperature + conventionHeatExchange);
    }

    private double convection() {
        System.out.println("CONEVETION BEGIN " + this.getTemperature());
        HeatCellType myType = this.getType();
        double alphaPrim = myType.getConvectionCoefficient() / (myType.getVolume() * myType.getDensity() * myType.getSpecificHeat());

        HeatCell lowerNeighbour = (HeatCell) getNeighbour(Dir.DOWN);
        HeatCell upperNeighbour = (HeatCell) getNeighbour(Dir.UP);
        double conventionChange = this.getTemperature() + alphaPrim * (this.getTemperature() - lowerNeighbour.getTemperature()) * myType.deltaTime();
        conventionChange += this.getTemperature() - alphaPrim * (upperNeighbour.getTemperature() - this.getTemperature()) * myType.deltaTime();
        System.out.println("CONEVETION END " + this.getTemperature());
        return conventionChange;
    }

    private double heatExchange() {
        HeatCellType type = this.getType();
        double heatTransferCoef = type.getHeatTransferCoefficient();
        double heatBalance = 0;
        double h = 1;
        for(Cell n : this.getNeighbours()) {
            if (n == null) continue;
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
