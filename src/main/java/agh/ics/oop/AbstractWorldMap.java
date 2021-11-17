package agh.ics.oop;


import java.util.ArrayList;


public abstract class AbstractWorldMap {
    protected final ArrayList<IMapElement> mapElements;

    public AbstractWorldMap() {
        mapElements = new ArrayList<>();
    }

    public String toString() {
        Vector2d[] boundaries = this.getDrawBoundaries();
        return new MapVisualizer((IWorldMap) this).draw(boundaries[0], boundaries[1]);
    }

    abstract protected Vector2d[] getDrawBoundaries();

    abstract public boolean canMoveTo(Vector2d position);

    public boolean place(IMapElement element) {
        Vector2d position = element.getPosition();
        if (canMoveTo(position)) {
            mapElements.add(element);
            return true;
        } else return false;
    }

    public boolean isOccupied(Vector2d position) {
        return (objectAt(position) != null);
    }

    public Object objectAt(Vector2d position) {
        for (IMapElement element : mapElements) {
            if (element.getPosition().equals(position)) {
                return element;
            }
        }
        return null;
    }
}
