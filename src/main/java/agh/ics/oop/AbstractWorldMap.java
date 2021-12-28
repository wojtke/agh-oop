package agh.ics.oop;


import java.util.HashMap;


public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected final HashMap<Vector2d, IMapElement> mapElements;
    private final MapVisualizer mapVisualizer;

    public AbstractWorldMap() {
        mapElements = new HashMap<>();
        mapVisualizer = new MapVisualizer(this);
    }

    public String toString() {
        Vector2d[] boundaries = this.getDrawBoundaries();
        return mapVisualizer.draw(boundaries[0], boundaries[1]);
    }

    abstract protected Vector2d[] getDrawBoundaries();

    public boolean canMoveTo(Vector2d position) {
        return !(objectAt(position) instanceof Animal);
    }

    public boolean place(Animal animal) throws IllegalArgumentException {
        Vector2d position = animal.getPosition();
        if (canMoveTo(position)) {
            mapElements.put(position, animal);
            animal.addObserver(this);
            return true;
        } else throw new IllegalArgumentException("Cannot place animal at" + position);
    }

    public boolean isOccupied(Vector2d position) {
        return (objectAt(position) != null);
    }

    public Object objectAt(Vector2d position) {
        return mapElements.get(position);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        mapElements.put(newPosition, (IMapElement) objectAt(oldPosition));
        mapElements.remove(oldPosition);
    }
}
