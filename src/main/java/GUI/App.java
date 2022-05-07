package GUI;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    private Stage primaryStage;
    private CAGrid grid;
    private Slider speedSlider;
    private int simulationUpdatePause, GUIUpdatePause;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        this.initialize();
        this.createGUI();

        Thread guiUpdateThread = new Thread(this::updateGui);
        guiUpdateThread.start();

        Thread simulationThread = new Thread(this::simulation);
        simulationThread.start();
    }

    private void initialize() {
        this.simulationUpdatePause = 100;
        this.GUIUpdatePause = 50;
    }

    private void simulation() {
        while (true) {
            try {
                Thread.sleep(simulationUpdatePause);
            } catch (InterruptedException ignore) {}

            this.grid.tickAll();
        }
    }

    private void createGUI() {
        this.grid = new CAGrid(this, 60, 30);

        this.speedSlider = new Slider(1, 100, 30);
        this.speedSlider.setPrefSize(200, 50);
        this.speedSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    simulationUpdatePause = 1000/newValue.intValue() - 5;
                    System.out.println(newValue.doubleValue());
                }
        );

        HBox controlBox = new HBox(this.speedSlider);
        controlBox.setAlignment(Pos.CENTER);

        VBox sceneVBox = new VBox(this.grid, controlBox);

        Scene scene = new Scene(sceneVBox);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    private void updateGui() {
        while (true) {
            try {
                Thread.sleep(GUIUpdatePause);
            } catch (InterruptedException ignore) {}

            this.grid.updateAll();
        }
    }
}
