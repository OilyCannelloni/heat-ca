package Components;

public interface I2DGrid<T> {
    int getWidth();
    int getHeight();
    default int getSize() {
        return getHeight() * getWidth();
    }
    T get(int x, int y);
    T get(Position position);
}
