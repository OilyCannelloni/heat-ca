package Models;

import GUI.Cell;

public class HeatCell extends Cell {

    public HeatCell() {
        super();
        this.setType(HeatCellType.DEFAULT);
    }

    private double heatExchange() {
        double heatTransferCoef = this.getType().getHeatTransferCoefficient();
        double heatBalance = 0;
        double h = 1;
        for(Cell neighbour : this.neighbours) {
            double delta_T = neighbour.getTemperature() - this.getTemperature();
            heatBalance += (heatTransferCoef * delta_T) / (h * h);
        }
        return heatBalance;
    }

    @Override
    public void onTick(int epoch) {
        ICellType myType = this.getType();
        double newTemperature = this.getTemperature();
        newTemperature += 1/(myType.getSpecificHeat() * myType.getDensity()) * heatExchange();
        this.setTemperature(newTemperature);
    }
}
