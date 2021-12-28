import agh.ics.oop.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class Vector2dTest {

    @Test
    public void equalsTest(){
        assertFalse(new Vector2d(1,2).equals("hehe"));
        assertTrue(new Vector2d(1,2).equals( new Vector2d(1, 2)) );
        assertFalse( new Vector2d(1,2).equals( new Vector2d(2, 1)) );
    }

    @Test
    public void toStringTest(){
        assertTrue( new Vector2d(1, 2).toString().equals("(1, 2)"));
    }

    @Test
    public void precedesTest(){
        assertTrue( new Vector2d(1, 2).precedes( new Vector2d(4, 6) ));
        assertTrue( new Vector2d(1, 1).precedes( new Vector2d(1, 1) ));
        assertFalse( new Vector2d(5, 2).precedes( new Vector2d(1, 1) ));
    }

    @Test
    public void followsTest(){
        assertTrue( new Vector2d(5, 2).follows( new Vector2d(1, 1) ));
        assertTrue( new Vector2d(1, 1).follows( new Vector2d(1, 1) ));
        assertFalse(new Vector2d(1, 2).follows( new Vector2d(4, 6) ));
    }

    @Test
    public void upperRight(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(5, 1);

        assertTrue(v1.upperRight(v2).equals( new Vector2d(5, 2) ));
        assertFalse(v1.upperRight(v2).equals( new Vector2d(5, 1) ));
    }

    @Test
    public void lowerLeft(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(5, 1);

        assertTrue(v1.lowerLeft(v2).equals( new Vector2d(1, 1) ));
        assertFalse(v1.lowerLeft(v2).equals( new Vector2d(5, 2) ));
    }

    @Test
    public void addTest(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(5, 1);

        assertTrue(v1.add(v2).equals( new Vector2d(6, 3)));
        assertTrue(v1.add(v1).equals( new Vector2d(2, 4)));

        assertFalse(v1.add(v2).equals( new Vector2d(0, 0) ));
    }

    @Test
    public void subtractTest(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(5, 1);

        assertTrue(v1.subtract(v2).equals( new Vector2d(-4, 1) ));
        assertTrue(v1.subtract(v1).equals( new Vector2d(0, 0) ));

        assertFalse(v1.subtract(v2).equals( new Vector2d(0, 0) ));
    }

    @Test
    public void oppositeTest(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(-5, 1);

        assertTrue(v1.opposite().equals( new Vector2d(-1, -2) ));
        assertTrue(v2.opposite().equals( new Vector2d(5, -1) ));

        assertFalse(v2.opposite().equals( new Vector2d(5, 1) ));
    }

}
