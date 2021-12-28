package agh.ics.oop;

public enum MoveDirection {
    FORWARD,
    BACKWARD,
    RIGHT,
    LEFT;

    public static MoveDirection fromString(String s) throws IllegalArgumentException {
        MoveDirection direction = switch (s) {
            case "f" -> MoveDirection.FORWARD;
            case "b" -> MoveDirection.BACKWARD;
            case "r" -> MoveDirection.RIGHT;
            case "l" -> MoveDirection.LEFT;
            default -> null;
        };
        if (direction == null) { throw new IllegalArgumentException("Invalid direction: " + s); }
        else { return direction; }
    }
}
