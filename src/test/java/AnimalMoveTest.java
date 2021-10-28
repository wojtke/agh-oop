import agh.ics.oop.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnimalMoveTest {
    @Test
    public void orientationTest1() {
        Animal animal = new Animal();

        MoveDirection[] directions = {
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT
        };
        MapDirection outcome = MapDirection.SOUTH;

        for (MoveDirection dir: directions) animal.move(dir);
        assertTrue(animal.getOri() == outcome);
    }

    @Test
    public void orientationTest2() {
        Animal animal = new Animal();

        MoveDirection[] directions = {
                MoveDirection.RIGHT,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.BACKWARD,
                MoveDirection.RIGHT,
        };
        MapDirection outcome = MapDirection.NORTH;

        for (MoveDirection dir: directions) animal.move(dir);
        assertTrue(animal.getOri() == outcome);
    }

    @Test
    public void positionTest1() {
        Animal animal = new Animal();

        MoveDirection[] directions = {
                MoveDirection.BACKWARD,
                MoveDirection.BACKWARD,
        };
        Vector2d outcome = new Vector2d(2,0);

        for (MoveDirection dir: directions) animal.move(dir);
        assertTrue(animal.getPos().equals(outcome));
    }

    @Test
    public void positionTest2() {
        Animal animal = new Animal();

        MoveDirection[] directions = {
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.BACKWARD,
        };
        Vector2d outcome = new Vector2d(3,4);

        for (MoveDirection dir: directions) animal.move(dir);
        assertTrue(animal.getPos().equals(outcome));
    }

    @Test
    public void mapBoundaryTest1() {
        Animal animal = new Animal();
        for (int i=0; i<20; i++) animal.move(MoveDirection.FORWARD);

        assertTrue( animal.getPos().precedes(new Vector2d(4, 4))
                && animal.getPos().follows(new Vector2d(0, 0)) );
    }

    @Test
    public void mapBoundaryTest2() {
        Animal animal = new Animal();
        for (int i=0; i<10; i++) {
            animal.move(MoveDirection.BACKWARD);
            animal.move(MoveDirection.RIGHT);
            animal.move(MoveDirection.BACKWARD);
            animal.move(MoveDirection.LEFT);
        }

        assertTrue( animal.getPos().precedes(new Vector2d(4, 4))
                && animal.getPos().follows(new Vector2d(0, 0)) );
    }

    @Test
    public void parseTest1() {
        String[] args = {"f", "b", "f", "r", "l"};
        MoveDirection[] directions = {
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.LEFT,
        };

        assertTrue(Arrays.equals( OptionsParser.parse(args), directions));
    }

    @Test
    public void parseTest2() {
        String[] args = {"r", "e", "heh", "f"};
        MoveDirection[] directions = {
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
        };

        assertTrue(Arrays.equals( OptionsParser.parse(args), directions));
    }


}
