import agh.ics.oop.MapDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class MapDirectionTest {

    @Test
    public void nextTest(){
        assertSame(MapDirection.NORTH.next(), MapDirection.EAST);
        assertSame(MapDirection.SOUTH.next(), MapDirection.WEST);
        assertSame(MapDirection.EAST.next(), MapDirection.SOUTH);
        assertSame(MapDirection.WEST.next(), MapDirection.NORTH);
    }

    @Test
    public void previousTest(){
        assertSame(MapDirection.EAST.previous(), MapDirection.NORTH);
        assertSame(MapDirection.WEST.previous(), MapDirection.SOUTH);
        assertSame(MapDirection.SOUTH.previous(), MapDirection.EAST);
        assertSame(MapDirection.NORTH.previous(), MapDirection.WEST);
    }
}
