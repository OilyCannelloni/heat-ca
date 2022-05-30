package GUI;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class DisplayCell extends StackPane {
    private Color color = null;
    private final ImageView imageView;

    public DisplayCell() {
        this.setColor(Color.WHITESMOKE);
        this.imageView = new ImageView();
        this.getChildren().add(this.imageView);
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

    public void setSymbol(Symbol symbol) {
        if (symbol == Symbol.EMPTY) {
            this.imageView.setImage(null);
            return;
        }
        Image image = symbol.getImage();
        this.imageView.setImage(image);
    }
}
