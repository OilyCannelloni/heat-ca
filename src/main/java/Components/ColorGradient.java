package Components;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ColorGradient {
    public static Color intermediate(Color c1, Color c2, double w1) {
        double w2 = 1 - w1;
        return new Color(
                c1.getRed() * w2 + c2.getRed() * w1,
                c1.getGreen() * w2 + c2.getGreen() * w1,
                c1.getBlue() * w2 + c2.getBlue() * w1,
                c1.getOpacity() * w2 + c2.getOpacity() * w1
        );
    }

    private final ArrayList<Color> colors;
    private final double intLen;

    public ColorGradient(Color... colors) {
        this.colors = new ArrayList<>(List.of(colors));
        intLen = 1.0 / (this.colors.size() - 1);
    }

    public Color getByPercentage(double p) {
        int nInterval = (int) (p / intLen);
        if (nInterval >= colors.size() - 1) nInterval = colors.size() - 2;
        if (nInterval < 0) nInterval = 0;
        double w1 = (p % intLen) / intLen;
        if (p == 1.0) w1 = 1.0;
        if (p == 0.0) w1 = 0.0;
        return intermediate(
                colors.get(nInterval),
                colors.get(nInterval + 1),
                w1
        );
    }
}
