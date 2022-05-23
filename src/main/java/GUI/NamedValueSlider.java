package GUI;

import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class NamedValueSlider extends VBox {
    private final Text nameText, valueText;
    private final Font font = new Font("Ubuntu Mono", 16);
    public Slider slider;

    public NamedValueSlider(String name, double min, double max, double val) {
        this.setPrefSize(300, 50);
        this.nameText = new Text();
        this.nameText.setFont(font);
        this.nameText.setText(name);
        this.valueText = new Text();
        this.valueText.setFont(font);
        this.setValue((int) val);
        this.slider = new Slider(min, max, val);
        this.slider.setPrefWidth(200);
        this.slider.valueProperty().addListener(
                (observable, oldValue, newValue) -> this.setValue(newValue.intValue())
        );
        slider.setMajorTickUnit(1);
        slider.setBlockIncrement(1);
        this.getChildren().addAll(nameText, slider, valueText);
    }

    private void setValue(int value) {
        this.valueText.setText(String.valueOf(value));
    }
}
