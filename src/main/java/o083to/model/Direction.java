package o083to.model;

public enum Direction {
    DOWN,
    RIGHT,
    UP,
    LEFT;

    public Direction turn(Direction turnTo) {
        return turnTo == Direction.LEFT ? turnLeft() : turnRight();
    }

    public Direction turnRight() {
        int ordinal = ordinal();
        int newOrdinal = ordinal == 0 ? 3 : ordinal - 1;
        return fromInteger(newOrdinal);
    }

    public Direction turnLeft() {
        int ordinal = ordinal();
        int newOrdinal = (ordinal + 1) % 4;
        return fromInteger(newOrdinal);
    }

    private static Direction fromInteger(int value) {
        switch (value) {
            case 0:
                return DOWN;
            case 1:
                return RIGHT;
            case 2:
                return UP;
            case 3:
                return LEFT;
            default: return null;
        }
    }
}
