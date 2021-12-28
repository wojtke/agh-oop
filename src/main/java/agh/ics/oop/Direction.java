package agh.ics.oop;

public enum Direction {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public Direction rotate(int n) {
        return Direction.values()[(this.ordinal() + n) % 8];
    }

    public static Direction random() {
        return Direction.values()[(int) (Math.random() * 8)];
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTHEAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTHEAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTHWEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTHWEST -> new Vector2d(-1, 1);
        };
    }

    public double getAngle() {
        return this.ordinal() * 360.0/8;
    }
}
