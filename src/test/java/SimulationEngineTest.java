import agh.ics.oop.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimulationEngineTest {

    @Test
    public void test1(){
        String[] strDirs = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        MoveDirection[] directions = OptionsParser.parse(strDirs);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };

        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        // are there animals where they are supposed to be
        assertTrue(map.objectAt(new Vector2d(2,0)) instanceof Animal);
        assertTrue(map.objectAt(new Vector2d(3,4)) instanceof Animal);

        // are there animals where they are not supposed to be
        assertTrue(map.objectAt(new Vector2d(3,3)) == null);
        assertTrue(map.objectAt(new Vector2d(2,2)) == null);
        assertTrue(map.objectAt(new Vector2d(0,1)) == null);

        // are the animals oriented the way they are supposed to be
        Animal a1 = (Animal) map.objectAt(new Vector2d(2,0));
        assertTrue(a1.getOrientation() == MapDirection.SOUTH);
        Animal a2 = (Animal) map.objectAt(new Vector2d(3,4));
        assertTrue(a2.getOrientation() == MapDirection.NORTH);
    }

    @Test
    public void test2(){
        String[] strDirs = {"f", "f", "r", "f", "f", "l", "f", "f", "r", "r", "r", "r", "b", "b"};
        MoveDirection[] directions = OptionsParser.parse(strDirs);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(0,0), new Vector2d(1,0) };

        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        // are there animals where they are supposed to be
        assertTrue(map.objectAt(new Vector2d(0,2)) instanceof Animal);
        assertTrue(map.objectAt(new Vector2d(3,1)) instanceof Animal);

        // are there animals where they are not supposed to be
        assertTrue(map.objectAt(new Vector2d(3,3)) == null);
        assertTrue(map.objectAt(new Vector2d(0,0)) == null);
        assertTrue(map.objectAt(new Vector2d(1,1)) == null);

        // are the animals oriented the way they are supposed to be
        Animal a1 = (Animal) map.objectAt(new Vector2d(0,2));
        assertTrue(a1.getOrientation() == MapDirection.EAST);
        Animal a2 = (Animal) map.objectAt(new Vector2d(3,1));
        assertTrue(a2.getOrientation() == MapDirection.WEST);
    }

    @Test
    public void test3(){
        String[] strDirs = {"f", "f", "r", "f", "f", "l", "f", "f"};
        MoveDirection[] directions = OptionsParser.parse(strDirs);
        IWorldMap map = new RectangularMap(4, 4);
        Vector2d[] positions = { new Vector2d(1,1) };

        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        // are there animals where they are supposed to be
        assertTrue(map.objectAt(new Vector2d(3,3)) instanceof Animal);

        // are there animals where they are not supposed to be
        assertTrue(map.objectAt(new Vector2d(3,2)) == null);
        assertTrue(map.objectAt(new Vector2d(0,0)) == null);
        assertTrue(map.objectAt(new Vector2d(1,1)) == null);

        // are the animals oriented the way they are supposed to be
        Animal a1 = (Animal) map.objectAt(new Vector2d(3,3));
        assertTrue(a1.getOrientation() == MapDirection.NORTH);
    }
    @Test
    public void test4(){
        String[] strDirs = {"f", "f", "f", "f",
                "r", "r", "r", "r",
                "f", "f", "f", "f",
                "b", "b", "b", "b",
                "r", "r", "r", "r",
                "f", "f", "f", "f",
                "r", "r", "r", "r",
                "b", "b", "b", "b",
                "r", "l", "l", "r",
                "f", "f", "f", "f",
                "f", "f", "f", "f",
                "f", "f", "f", "f",
                "f", "f", "f", "f"};
        MoveDirection[] directions = OptionsParser.parse(strDirs);
        IWorldMap map = new RectangularMap(7, 7);
        Vector2d[] positions = { new Vector2d(0,0), new Vector2d(0,6), new Vector2d(6,6), new Vector2d(6,0) };

        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        // are there animals where they are supposed to be
        assertTrue(map.objectAt(new Vector2d(1,3)) instanceof Animal);
        assertTrue(map.objectAt(new Vector2d(1,2)) instanceof Animal);
        assertTrue(map.objectAt(new Vector2d(6,3)) instanceof Animal);
        assertTrue(map.objectAt(new Vector2d(6,2)) instanceof Animal);

        // are there animals where they are not supposed to be

        assertTrue(map.objectAt(new Vector2d(1,4)) == null);
        assertTrue(map.objectAt(new Vector2d(0,0)) == null);
        assertTrue(map.objectAt(new Vector2d(3,3)) == null);
        assertTrue(map.objectAt(new Vector2d(1,1)) == null);
        assertTrue(map.objectAt(new Vector2d(6,6)) == null);


        // are the animals oriented the way they are supposed to be
        Animal a1 = (Animal) map.objectAt(new Vector2d(1,2));
        assertTrue(a1.getOrientation() == MapDirection.NORTH);
        Animal a2 = (Animal) map.objectAt(new Vector2d(1,3));
        assertTrue(a2.getOrientation() == MapDirection.SOUTH);

        Animal a3 = (Animal) map.objectAt(new Vector2d(6,2));
        assertTrue(a3.getOrientation() == MapDirection.NORTH);
        Animal a4 = (Animal) map.objectAt(new Vector2d(6,3));
        assertTrue(a4.getOrientation() == MapDirection.SOUTH);
    }
}
