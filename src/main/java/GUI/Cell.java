package GUI;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Cell extends StackPane {
    private Color color;
    private boolean needsUpdate;

    public Cell() {
        this.setColor(Color.RED);
        this.setOpacity(1.0);
        this.setPrefSize(15, 15);
        this.update();
    }

    public void setColor(Color color) {
        this.color = color;
        this.needsUpdate = true;
    }

    public void update() {
        this.setBackground(new Background(new BackgroundFill(
                this.color,
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));
    }
}
