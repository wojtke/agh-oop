import agh.ics.oop.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorldMapTest {

    @Test
    public void rectangularPlaceTest() {
        IWorldMap map = new RectangularMap(5, 5);
        Vector2d position = new Vector2d(2, 2);
        Animal animal = new Animal(map, position);

        // simple place
        assertTrue(map.place(animal));

        assertTrue(map.isOccupied(position));
        assertTrue(map.objectAt(position).equals(animal));

        // place at occupied position
        Animal other_animal = new Animal(map, position);
        assertThrows(IllegalArgumentException.class, () -> map.place(other_animal));

        assertTrue(map.objectAt(position).equals(animal));
    }

    @Test
    public void grassfieldPlaceTest() {
        IWorldMap map = new GrassField(5);
        Vector2d position = new Vector2d(2, 2);
        Animal animal = new Animal(map, position);

        // simple place
        assertTrue(map.place(animal));

        assertTrue(map.isOccupied(position));
        assertTrue(map.objectAt(position).equals(animal));

        // place at occupied position
        Animal other_animal = new Animal(map, position);
        assertThrows(IllegalArgumentException.class, () -> map.place(other_animal));

        assertTrue(map.objectAt(position).equals(animal));

        // place at far away position and negative position
        Vector2d far_position = new Vector2d(100, 1231233);
        Vector2d negative_position = new Vector2d(-1, -1);
        assertTrue(map.place(new Animal(map, far_position)));
        assertTrue(map.place(new Animal(map, negative_position)));

        assertTrue(map.isOccupied(far_position));
        assertTrue(map.isOccupied(negative_position));
    }

    @Test
    public void canMoveToTest() {
        IWorldMap grassFieldMap = new GrassField(5);

        //anything can move freely - no obstruction or boundaries
        assertTrue(grassFieldMap.canMoveTo(new Vector2d(0, 0)));
        assertTrue(grassFieldMap.canMoveTo(new Vector2d(-12, 2)));
        assertTrue(grassFieldMap.canMoveTo(new Vector2d(12313132, 1312123)));

        //cannot move to position occupied by an animal
        Animal animal = new Animal(grassFieldMap, new Vector2d(2, 2));
        grassFieldMap.place(animal);
        assertFalse(grassFieldMap.canMoveTo(new Vector2d(2, 2)));

        //on rectangular map we need to respect boundaries, also no negative positions
        IWorldMap rectMap = new RectangularMap(5, 5);
        assertTrue(rectMap.canMoveTo(new Vector2d(0, 0)));
        assertTrue(rectMap.canMoveTo(new Vector2d(4, 2)));
        assertFalse(rectMap.canMoveTo(new Vector2d(5, 2)));
        assertFalse(rectMap.canMoveTo(new Vector2d(-12, 4)));
        assertFalse(rectMap.canMoveTo(new Vector2d(123, 123)));
    }

    @Test
    public void grassfieldAnimalOverGrassTest() {
        IWorldMap map = new GrassField(1);

        //look for grass
        int grasscount = 0;
        Vector2d grassPosition = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++){
                if (map.objectAt(new Vector2d(i, j)) instanceof Grass) {
                    grasscount++;
                    grassPosition = new Vector2d(i, j);
                }
            }
        }
        assertTrue(grasscount == 1);

        //should be occupied (by grass)
        assertTrue(map.isOccupied(grassPosition));
        assertTrue(map.objectAt(grassPosition) instanceof Grass);

        //animal should be able to move here
        assertTrue(map.canMoveTo(grassPosition));

        Animal animal = new Animal(map, grassPosition);
        assertTrue(map.place(animal));

        //now it should be occupied by animal
        assertTrue(map.isOccupied(grassPosition));
        assertTrue(map.objectAt(grassPosition).equals(animal));

        //look for grass
        grasscount = 0;
        grassPosition = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++){
                if (map.objectAt(new Vector2d(i, j)) instanceof Grass) {
                    grasscount++;
                    grassPosition = new Vector2d(i, j);
                }
            }
        }
        assertTrue(grasscount == 1);
    }

}
