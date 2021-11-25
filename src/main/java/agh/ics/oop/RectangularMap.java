package agh.ics.oop;

public class RectangularMap extends AbstractWorldMap {
    private final Vector2d lowerLeftBound, upperRightBound;

    public RectangularMap(int width, int height) {
        lowerLeftBound = new Vector2d(0,0);
        upperRightBound = new Vector2d(width-1, height-1);
    }

    @Override
    protected Vector2d[] getDrawBoundaries() {
        return new Vector2d[]{lowerLeftBound, upperRightBound};
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position.follows(lowerLeftBound)
                && position.precedes(upperRightBound)
                && !isOccupied(position));
    }
}
