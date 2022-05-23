package Components;

import Backend.Algorithm;

public enum Dir {
    UP,
    LEFT,
    RIGHT,
    DOWN,
    FRONT,
    BACK;

    public static Dir random() {
        return Dir.values()[Algorithm.random.nextInt(4)];
    }

    public static Dir random3D() {
        return Dir.values()[Algorithm.random.nextInt(6)];
    }
}
