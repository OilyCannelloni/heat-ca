package GUI;

import Models.HeatScenario;
import Models.ICellType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    private Stage primaryStage;
    private DisplayGrid displayGrid;
    private ComboBox<ICellType> typeSelectBox;
    private int simulationUpdatePause, GUIUpdatePause;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.displayGrid = new DisplayGrid(60, 30);

        this.initialize();
        this.buildGrid();
        this.createGUI();

        Thread guiUpdateThread = new Thread(this::updateGui);
        guiUpdateThread.start();

        Thread simulationThread = new Thread(this::simulation);
        simulationThread.start();
    }

    private void buildGrid() {
        HeatScenario scenario = new HeatScenario();
        this.displayGrid.gridStack = scenario.build(60, 30, 7);
    }

    private void initialize() {
        this.simulationUpdatePause = 100;
        this.GUIUpdatePause = 15;
    }

    private void simulation() {
        while (true) {
            try {
                Thread.sleep(simulationUpdatePause);
            } catch (InterruptedException ignore) {}

            this.displayGrid.gridStack.tickAll();
        }
    }

    private void createGUI() {
        NamedValueSlider speedSlider = new NamedValueSlider("Simulation Speed [ticks/s]", 1, 100, 30);
        speedSlider.slider.valueProperty().addListener(
                (observable, oldValue, newValue) -> simulationUpdatePause = 1000/newValue.intValue() - 5
        );
        speedSlider.slider.setShowTickLabels(true);
        speedSlider.slider.setShowTickMarks(true);

        NamedValueSlider layerSlider = new NamedValueSlider(
                "Layer",0, displayGrid.gridStack.size() - 1, 0
        );
        layerSlider.slider.valueProperty().addListener(
                (observable, oldValue, newValue) -> displayGrid.gridStack.setActiveIndex(newValue.intValue())
        );
        layerSlider.slider.setShowTickMarks(true);
        layerSlider.slider.setShowTickLabels(true);
        layerSlider.slider.setMinorTickCount(0);
        layerSlider.slider.setSnapToTicks(true);

        HBox controlBox = new HBox(speedSlider, layerSlider);
        controlBox.setAlignment(Pos.CENTER);

        VBox sceneVBox = new VBox(this.displayGrid, controlBox);

        Scene scene = new Scene(sceneVBox);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    private void updateGui() {
        while (true) {
            try {
                Thread.sleep(GUIUpdatePause);
            } catch (InterruptedException ignore) {}

            Platform.runLater(() -> this.displayGrid.updateAll());
        }
    }
}
