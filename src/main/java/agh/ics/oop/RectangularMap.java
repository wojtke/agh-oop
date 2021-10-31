package agh.ics.oop;


public class RectangularMap implements IWorldMap {
    private final Vector2d upperRightBound;
    private final Vector2d lowerLeftBound;
    private final Animal[][] map;

    public RectangularMap(int width, int height) {
        lowerLeftBound = new Vector2d(0,0);
        upperRightBound = new Vector2d(width-1, height-1);
        map = new Animal[width][height];
    }

    public String toString() {
        return new MapVisualizer(this).draw(lowerLeftBound, upperRightBound);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.follows(lowerLeftBound) && position.precedes(upperRightBound)){
            return !isOccupied(position);
        } else return false;
    }

    @Override
    public boolean place(Animal animal) {
        Vector2d position = animal.getPosition();
        if (canMoveTo(position)) {
            map[position.x][position.y] = animal;
            return true;
        } else return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return (map[position.x][position.y] != null);
    }

    @Override
    public Object objectAt(Vector2d position) {
        return map[position.x][position.y];
    }

    @Override
    public boolean move(Vector2d old_position, Vector2d new_position){
        if (canMoveTo(new_position)) {
            map[new_position.x][new_position.y] = map[old_position.x][old_position.y];
            map[old_position.x][old_position.y] = null;
            return true;
        } else return false;
    }


}
