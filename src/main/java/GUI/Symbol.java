package GUI;

import javafx.scene.image.Image;

public enum Symbol {
    EMPTY,
    CROSS,
    DOTS,
    CIRCLE_WITH_DOT;

    // TODO jak ktoś wie jak to napisac lepiej to chętnie się dowiem (inicjalizacja pola statycznego w enumie)
    // wystarczy sposób na wywołanie jakiegoś "konstruktora" ale tak żeby się wywołał tylko raz
    private static final Image[] images = {
            null,
            new Image(CROSS.getPath()),
            new Image(DOTS.getPath()),
            new Image(CIRCLE_WITH_DOT.getPath())
    };

    public String getPath() {
        return switch (this) {
            case CROSS -> "file:.\\images\\cross.png";
            case EMPTY -> null;
            case DOTS -> "file:.\\images\\dots.png";
            case CIRCLE_WITH_DOT -> "file:.\\images\\circle_with_dot.png";
        };
    }

    public Image getImage() {
        if (this == Symbol.EMPTY) return null;
        return Symbol.images[this.ordinal()];
    }
}
