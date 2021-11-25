package agh.ics.oop;

import java.util.HashMap;

public class GrassField extends AbstractWorldMap {
    protected final HashMap<Vector2d, Grass> grassMap;

    public GrassField(int n) {
        super();
        grassMap = new HashMap<>();

        int randomMax = (int) Math.sqrt(n*10);
        while (n > 0) {
            Vector2d randomPosition = new Vector2d( (int) (Math.random()*randomMax), (int) (Math.random()*randomMax));
            if (placeGrass(randomPosition)) n--;
        }
    }

    @Override
    protected Vector2d[] getDrawBoundaries() {
        Vector2d[] boundaries = {null, null};
        for (Vector2d position : animalMap.keySet()) {
            boundaries[0] = boundaries[0].lowerLeft(position);
            boundaries[1] = boundaries[1].upperRight(position);
        }
        return boundaries;
    }

    @Override
    public Object objectAt(Vector2d position) {
        if (animalMap.get(position) != null) return animalMap.get(position);
        else return grassMap.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(objectAt(position) instanceof Animal);
    }

    public boolean placeGrass(Vector2d position) {
        if (objectAt(position) == null) {
            grassMap.put(position, new Grass(position));
            return true;
        } else return false;
    }
}
