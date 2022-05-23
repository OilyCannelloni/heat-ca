package GUI;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class DisplayCell extends StackPane {
    private Color color = null;

    public DisplayCell() {
        this.setColor(Color.WHITESMOKE);
        this.setOpacity(1.0);
        this.setPrefSize(15, 15);
    }

    public void setColor(Color color) {
        if (this.color == color) return;
        this.color = color;
        this.setBackground(new Background(new BackgroundFill(
                color,
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));
    }
}
