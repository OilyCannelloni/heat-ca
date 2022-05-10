package Models;

import GUI.Cell;

public class HeatCell extends Cell {
    public HeatCell() {
        super();
        this.setType(HeatCellType.DEFAULT);
    }

    private double heatExchange() {
        double heatExchangeCoef = this.getType().getHeatTransferCoefficient();
        double heatBalance = 0;
        double h = 0.2;
        for(Cell neighbour : this.neighbours) {
            heatBalance += (heatExchangeCoef * neighbour.getTemperature()) / ((0.2) * (0.2));
        }
        return heatBalance;
    }
    @Override
    public void onTick(int epoch) {
        ICellType myType = this.getType();
        double newTemperature = this.getTemperature();
        newTemperature += 1/(myType.getHeatTransferCoefficient() * myType.getDensity()) * heatExchange();
        this.setTemperature(newTemperature);
    }
}
