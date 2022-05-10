package GUI;

import Components.Dir;
import Models.ICellType;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Cell extends StackPane {
    private ICellType type;
    private Color color;
    private boolean needsUpdate, stateChanged;
    protected Cell[] neighbours;
    private double temperature;

    public Cell() {
        this.neighbours = new Cell[4];
        this.setColor(Color.WHITESMOKE);
        this.setOpacity(1.0);
        this.setPrefSize(15, 15);
    }

    public ICellType getType() {
        return this.type;
    }

    public void setType(ICellType type) {
        this.type = type;
        this.setStateChanged();
    }

    public void addNeighbour(Dir d, Cell cell) {
        this.neighbours[d.ordinal()] = cell;
    }

    public void setColor(Color color) {
        this.color = color;
        this.needsUpdate = true;
    }

    public void setUpdate() {
        this.needsUpdate = true;
    }

    public void setStateChanged() {
        this.stateChanged = true;
        this.needsUpdate = true;
    }

    private void clearFlags() {
        this.needsUpdate = false;
        this.stateChanged = false;
    }

    public boolean needsUpdate() {
        return this.needsUpdate;
    }

    public void beforeUpdate() {
        this.setColor(this.getColor());
    }

    public void update() {
        this.beforeUpdate();
        this.setBackground(new Background(new BackgroundFill(
                this.color,
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));
    }

    public void onTickBase(int epoch) {
        if (this.stateChanged) {
            this.stateChanged = false;
            return;
        }
        this.onTick(epoch);
    }

    public void onTick(int epoch) {

    }

    public Cell getNeighbour(Dir d) {
        return this.neighbours[d.ordinal()];
    }

    public double getTemperature() { return this.temperature;}

    public void setTemperature(double newTemp) { this.temperature = newTemp;}

    private Color getColor() {
        /*Color warmestColor = Color.rgb(255,13,13);
        Color coldestColor = Color.rgb(0,43,142);
        double percent = this.getTemperature() / 100;
        int resultRed = (int) (coldestColor.getRed() + percent * (warmestColor.getRed() - coldestColor.getRed()));
        int resultGreen = (int) (coldestColor.getGreen() + percent * (warmestColor.getGreen() - coldestColor.getGreen()));
        int resultBlue = (int) (coldestColor.getBlue() + percent * (warmestColor.getBlue() - coldestColor.getBlue()));
        System.out.println(coldestColor.getRed() + " " + warmestColor.getRed() + " " + resultBlue);*/
        double curTemp = this.getTemperature();
        if(curTemp >= 90) {
            return Color.rgb(255,13,13);
        }
        if(80 <= curTemp) {
            return Color.rgb(255,13,13);
        }
        if(70 <= curTemp) {
            return Color.rgb(255,13,13);
        }
        if(60 <= curTemp) {
            return Color.rgb(255,13,13);
        }
        if(50 <= curTemp) {
            return Color.rgb(255,13,13);
        }
        if(40 <= curTemp) {
            return Color.rgb(255,13,13);
        }
        if(30 <= curTemp) {
            return Color.rgb(255,13,13);
        }
        if(20 <= curTemp) {
            return Color.rgb(255,13,13);
        }
        if(10 <= curTemp) {
            return Color.rgb(255, 13, 13);
        }
        else {
            return Color.rgb(255,13,13);
        }

        return Color.rgb(resultRed, resultGreen, resultBlue);
    }
}
