package Components;

import GUI.Symbol;
import javafx.scene.paint.Color;

public interface ICell {
    void onTick(int epoch);
    Color getColor();
    Symbol getSymbol();
}
