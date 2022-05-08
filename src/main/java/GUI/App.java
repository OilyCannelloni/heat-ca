package GUI;

import Models.EpidemicCell;
import Models.EpidemicScenario;
import Models.ICellType;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    private Stage primaryStage;
    private CAGrid grid;
    private Slider speedSlider;
    private ComboBox<ICellType> typeSelectBox;
    private int simulationUpdatePause, GUIUpdatePause;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        this.initialize();
        this.buildGrid();
        this.createGUI();

        Thread guiUpdateThread = new Thread(this::updateGui);
        guiUpdateThread.start();

        Thread simulationThread = new Thread(this::simulation);
        simulationThread.start();
    }

    private void buildGrid() {
        EpidemicScenario scenario = new EpidemicScenario();
        this.grid = scenario.build(60, 30);
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
        this.speedSlider = new Slider(1, 100, 30);
        this.speedSlider.setPrefSize(200, 50);
        this.speedSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    simulationUpdatePause = 1000/newValue.intValue() - 5;
                }
        );

        this.typeSelectBox = new ComboBox<>();


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

            this.grid.updateWhereNeeded();
        }
    }
}
