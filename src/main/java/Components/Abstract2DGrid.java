package Components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Abstract2DGrid<T> implements I2DGrid<T> {
    protected final int width, height;
    private final ArrayList<Integer> updateOrder;

    public Abstract2DGrid(int width, int height) {
        this.width = width;
        this.height = height;
        this.updateOrder = new ArrayList<>();
        for (int i = 0; i < this.getSize(); i++) this.updateOrder.add(i);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public T get(int x, int y) {
        return null;
    }

    @Override
    public T get(Position position) {
        return null;
    }


    public Iterable<Position> positions() {
        return () -> new Iterator<>() {
            private int x, y;

            @Override
            public boolean hasNext() {
                return y * width + x < getSize();
            }

            @Override
            public Position next() {
                Position ret = new Position(x, y);
                if (++x == width) {
                    x = 0;
                    y++;
                }
                return ret;
            }
        };
    }


    public Iterable<T> elements() {
        return () -> new Iterator<>() {
            private int x, y;

            @Override
            public boolean hasNext() {
                return y * width + x < getSize();
            }

            @Override
            public T next() {
                T ret = get(x, y);
                if (++x == width) {
                    x = 0;
                    y++;
                }
                return ret;
            }
        };
    }


    public Iterable<T> shuffledElements() {
        return new Iterable<>() {
            class ShuffledGridIterator implements Iterator<T> {
                private int i;

                public ShuffledGridIterator() {
                    Collections.shuffle(updateOrder);
                }

                @Override
                public boolean hasNext() {
                    return i < getSize() - 1;
                }

                @Override
                public T next() {
                    int linearIndex = updateOrder.get(i++);
                    return get(
                            linearIndex % width,
                            linearIndex / width
                    );
                }
            }

            @Override
            public Iterator<T> iterator() {
                return new ShuffledGridIterator();
            }
        };
    }
}

