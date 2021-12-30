package agh.ics.oop;

public class MapWrappy extends Map {

    public MapWrappy(int width, int height, int jungle_width, int jungle_height) {
        super(width, height, jungle_width, jungle_height);
    }

    @Override
    public Vector2d moveInterpreter(Vector2d new_position) {
        if (new_position != null) {
            if (!inBoundaries(new_position)) {
                int x = Math.floorMod(new_position.x, (upperRightBound.x - lowerLeftBound.x+1)) + lowerLeftBound.x;
                int y = Math.floorMod(new_position.y, (upperRightBound.y - lowerLeftBound.y+1)) + lowerLeftBound.y;
                new_position = new Vector2d(x, y);
            }
            return new_position;
        }
        return null;
    }
}
