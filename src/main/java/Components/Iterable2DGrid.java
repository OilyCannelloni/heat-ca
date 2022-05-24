package Components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public interface Iterable2DGrid<T> {
    int getGridWidth();
    int getGridHeight();
    default int getSize() {
        return getGridHeight() * getGridWidth();
    }
    T get(int x, int y);
    T get(Position position);


    default Iterable<Position> positions() {
        return () -> new Iterator<>() {
            private int x, y;

            @Override
            public boolean hasNext() {
                return y * getGridWidth() + x < getSize();
            }

            @Override
            public Position next() {
                Position ret = new Position(x, y);
                if (++x == getGridWidth()) {
                    x = 0;
                    y++;
                }
                return ret;
            }
        };
    }


    default Iterable<T> elements() {
        return () -> new Iterator<>() {
            private int x, y;

            @Override
            public boolean hasNext() {
                return y * getGridWidth() + x < getSize();
            }

            @Override
            public T next() {
                T ret = get(x, y);
                if (++x == getGridWidth()) {
                    x = 0;
                    y++;
                }
                return ret;
            }
        };
    }

    default Iterable<T> shuffledElements() {
        return new Iterable<>() {
            class ShuffledGridIterator implements Iterator<T> {
                private final ArrayList<Integer> updateOrder;
                private int i;

                public ShuffledGridIterator() {
                    updateOrder = new ArrayList<>();
                    for (int i = 0; i < getSize(); i++) updateOrder.add(i);
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
                            linearIndex % getGridWidth(),
                            linearIndex / getGridWidth()
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
