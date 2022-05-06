package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private Stage primaryStage;
    private CAGrid grid;


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        this.initialize();
        this.createGUI();

        Thread guiUpdateThread = new Thread(this::updateGui);
        guiUpdateThread.start();
    }

    private void initialize() {

    }

    private void createGUI() {
        this.grid = new CAGrid(this, 40, 30);

        Scene scene = new Scene(this.grid);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    private void updateGui() {
        while (true) {
            // sleep
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignore) {}

            this.grid.updateAll();
        }
    }
}
