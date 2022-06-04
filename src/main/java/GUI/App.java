package GUI;

import Components.Scenario;
import Models.BasicRoomHeatScenario;
import Models.HeatScenario;
import Models.SecondRoomHeatScenario;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class App extends Application {
    private Stage primaryStage;
    private DisplayGrid displayGrid;
    private final Timer GUITimer = new Timer();
    private int simulationUpdatePause;
    private Thread simulationThread;
    private Scenario scenario;

    private final int roomWidth = 30;
    private final int roomHeight = 20;
    private final int roomDepth = 15;

    class GUIUpdate extends TimerTask {
        @Override
        public void run() {
            Platform.runLater(() -> displayGrid.updateAll());
        }
    }

    private void runSimulationForTicks(long ticks) {
        while (ticks-- >= 0) {
            this.displayGrid.gridStack.tickAllNoShuffle();
        }
    }

    private void simulation() {
        while (true) {
            try {
                //noinspection BusyWait
                Thread.sleep(simulationUpdatePause);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            this.displayGrid.gridStack.tickAll();
        }
    }

    @Override
    public void stop() {
        simulationThread.interrupt();
        Platform.exit();
        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.displayGrid = new DisplayGrid(roomWidth, roomHeight);

        this.initialize();
        this.buildGrid();
        this.createGUI();

        int GUIUpdatePause = 15;
        GUITimer.schedule(new GUIUpdate(), 0, GUIUpdatePause);

        simulationThread = new Thread(this::simulation);
        simulationThread.start();
    }

    private void buildGrid() {
//        scenario = new BasicRoomHeatScenario();
        scenario = new SecondRoomHeatScenario();
        this.displayGrid.gridStack = scenario.build(roomWidth, roomHeight, roomDepth);
    }

    private void initialize() {
        this.simulationUpdatePause = 100;
    }

    private void createGUI() {
        NamedValueSlider speedSlider = new NamedValueSlider("Simulation Speed [ticks/s]", 1, 100, 5);
        speedSlider.slider.valueProperty().addListener(
                (observable, oldValue, newValue) -> simulationUpdatePause = 1000/newValue.intValue() - 10
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

        Button fastForwardButton = new Button();
        fastForwardButton.setText("Fast Forward 500 ticks");
        fastForwardButton.setOnAction(event -> {
            simulationThread.interrupt();
            runSimulationForTicks(500);
            simulationThread = new Thread(this::simulation);
            simulationThread.start();
        });

        HBox controlBox = new HBox(speedSlider, layerSlider);
        controlBox.setAlignment(Pos.CENTER);

        Label actionLabel = new Label();
        Button statButton = new Button();
        statButton.setText("Print value");
        statButton.setOnAction(event -> {
            String s = scenario.printAction();
            actionLabel.setText(s);
        });

        HBox statBox = new HBox(fastForwardButton, statButton, actionLabel);


        VBox sceneVBox = new VBox(this.displayGrid, controlBox, statBox);

        Scene scene = new Scene(sceneVBox);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }
}
