package Components;

import Backend.Algorithm;

public enum Dir {
    UP,
    LEFT,
    RIGHT,
    DOWN;

    public static Dir random() {
        return Dir.values()[Algorithm.random.nextInt(Dir.values().length)];
    }
}
