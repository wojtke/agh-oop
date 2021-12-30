package agh.ics.oop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Map{
    protected final Vector2d lowerLeftBound, upperRightBound;
    private final Vector2d jungleLowerLeftBound, jungleUpperRightBound;

    private final HashMap<Vector2d, ArrayList<Animal>> animalMap = new HashMap<>();
    private final HashMap<Vector2d, Grass> grassMap = new HashMap<>();

    public Map(int width, int height, int jungle_width, int jungle_height) {
        if (jungle_width > width || jungle_height > height) {
            throw new IllegalArgumentException("Jungle is bigger than map");
        }

        this.lowerLeftBound = new Vector2d(0,0);
        this.upperRightBound = new Vector2d(width-1, height-1);

        this.jungleLowerLeftBound = new Vector2d((width - jungle_width)/2, (height - jungle_height)/2);
        this.jungleUpperRightBound = new Vector2d((width + jungle_width)/2-1, (height + jungle_height)/2-1);
    }

    public void putAnimal(Animal animal, Vector2d position) {
        if(this.animalMap.containsKey(position)) {
            this.animalMap.get(position).add(animal);
        } else {
            ArrayList<Animal> animals = new ArrayList<>();
            animals.add(animal);
            this.animalMap.put(position, animals);
        }
    }

    public void removeAnimal(Animal animal, Vector2d position) {
        if(this.animalMap.containsKey(position)) {
            animalMap.get(position).remove(animal);
        } else {
            throw new IllegalArgumentException("Animal is not on the position");
        }
    }

    public void move(Animal animal, Vector2d new_position) {
        removeAnimal(animal, animal.getPosition());
        putAnimal(animal, new_position);
    }

    public ArrayList<Animal> getAnimalsAt(Vector2d position) {
        if (this.animalMap.containsKey(position)) {
            if (this.animalMap.get(position).size() > 0) {
                return this.animalMap.get(position);
            }
        }
        return null;
    }

    public int getAnimalCountAt(Vector2d position) {
        if (this.animalMap.containsKey(position)) {
            return this.animalMap.get(position).size();
        }
        return 0;
    }


    public int getGrassCount() {
        return this.grassMap.size();
    }

    public boolean isPlantAt(Vector2d position) {
        return this.grassMap.containsKey(position);
    }

    public void removePlant(Vector2d position) {
        this.grassMap.remove(position);
    }

    public void growGrass() {
        Vector2d position;

        position = getRandomUnoccupiedPosition("jungle");
        if (position != null) {
            Grass grass = new Grass(position);
            this.grassMap.put(position, grass);
        }

        position = getRandomUnoccupiedPosition("not_jungle");
        if (position != null) {
            Grass grass = new Grass(position);
            this.grassMap.put(position, grass);
        }
    }


    private boolean isOccupied(Vector2d position) {
        if (this.animalMap.containsKey(position)){
            if(this.animalMap.get(position).size() > 0) {
                return true;
            }
        }
        return this.grassMap.containsKey(position);
    }

    private boolean isInJungle(Vector2d position) {
        return (position.precedes(jungleUpperRightBound) && position.follows(jungleLowerLeftBound));
    }

    public IMapElement objectAt(Vector2d position) {
        if (this.animalMap.containsKey(position)) {
            if(this.animalMap.get(position).size() > 0) {
                return this.animalMap.get(position).stream().max(Comparator.comparingInt(Animal::getEnergy)).get();
            }
        }
        return this.grassMap.getOrDefault(position, null);
    }


    public Vector2d getRandomUnoccupiedPosition(String area) {
        return switch (area) {
            case "jungle" -> {
                for (int i = 0; i < 100; i++) {
                    Vector2d position = getRandomPosition(jungleLowerLeftBound, jungleUpperRightBound);
                    if (!isOccupied(position)) {
                        yield position;
                    }
                } yield null;
            }
            case "not_jungle" -> {
                for (int i = 0; i < 1000; i++) {
                    Vector2d position = getRandomPosition(lowerLeftBound, upperRightBound);
                    if (!isOccupied(position) && !isInJungle(position)) {
                        yield position;
                    }
                }
                yield null;
            }
            case "map" -> {
                for (int i = 0; i < 100; i++) {
                    Vector2d position = getRandomPosition(lowerLeftBound, upperRightBound);
                    if (!isOccupied(position)) {
                        yield position;
                    }
                } yield null;
            }
            default -> throw new IllegalArgumentException("Area not found");
        };
    }

    private Vector2d getRandomPosition(Vector2d lowerLeft, Vector2d upperRight) {

        int min_x = lowerLeft.x;
        int max_x = upperRight.x;
        int min_y = lowerLeft.y;
        int max_y = upperRight.y;


        return new Vector2d(
                (int) (Math.random()*(1+max_x-min_x)) + min_x,
                (int) (Math.random()*(1+max_y-min_y)) + min_y
            );

    }

    public Vector2d[] getBoundaries() {
        return new Vector2d[]{lowerLeftBound, upperRightBound};
    }

    public Vector2d[] getJungleBoundaries() {
        return new Vector2d[]{jungleLowerLeftBound, jungleUpperRightBound};
    }

    protected boolean inBoundaries(Vector2d position) {
        return(position.precedes(upperRightBound) && position.follows(lowerLeftBound));
    }

    public Vector2d moveInterpreter(Vector2d new_position) {
        if (new_position != null) {
            if(inBoundaries(new_position)) {
                return new_position;
            }
        }
        return null;
    }
}
