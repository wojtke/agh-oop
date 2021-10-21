package agh.ics.oop;

public enum MoveDirection {
    FORWARD,
    BACKWARD,
    RIGHT,
    LEFT;

    public static MoveDirection fromString(String s) {
        return switch (s) {
            case "f" -> MoveDirection.FORWARD;
            case "b" -> MoveDirection.BACKWARD;
            case "r" -> MoveDirection.RIGHT;
            case "l" -> MoveDirection.LEFT;
            default -> null;
        };
    }
}
