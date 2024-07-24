package util;

import org.junit.Test;
import utils.pathfinder.Position;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PositionTest {

    @Test
    public void testCalculateRotation() {
        final var p1 = new Position(0, 0);
        final var p2 = new Position(1, 1);
        assertEquals(3, Position.calculateRotation(p1, p2));
    }

    @Test
    public void testGetInvertedRotation() {
        assertEquals(Position.SOUTH_WEST, Position.getInvertedRotation(Position.NORTH_EAST));
        assertEquals(Position.NORTH_EAST, Position.getInvertedRotation(Position.SOUTH_WEST));
        assertEquals(Position.NORTH_WEST, Position.getInvertedRotation(Position.SOUTH_EAST));
        assertEquals(Position.SOUTH_EAST, Position.getInvertedRotation(Position.NORTH_WEST));
        assertEquals(Position.NORTH, Position.getInvertedRotation(Position.SOUTH));
        assertEquals(Position.SOUTH, Position.getInvertedRotation(Position.NORTH));
        assertEquals(Position.EAST, Position.getInvertedRotation(Position.WEST));
        assertEquals(Position.WEST, Position.getInvertedRotation(Position.EAST));
    }

    @Test
    public void testCalculatePosition() {
        final var p = Position.calculatePosition(0, 0, Position.NORTH, false, 1);
        assertEquals(new Position(0, -1), p);
    }

    @Test
    public void testMakeSquareInclusive() {
        final var p1 = new Position(0, 0);
        final var p2 = new Position(1, 1);
        final var expected = List.of(new Position(0, 0), new Position(0, 1), new Position(1, 0), new Position(1, 1));
        assertEquals(expected, Position.makeSquareInclusive(p1, p2));
    }

    @Test
    public void testAdd() {
        final var p1 = new Position(1, 1, 1.0);
        final var p2 = new Position(2, 2, 2.0);
        final var result = p1.add(p2);
        assertEquals(new Position(3, 3, 3.0d), result);
    }

    @Test
    public void testSubtract() {
        final var p1 = new Position(3, 3, 3.0);
        final var p2 = new Position(1, 1, 1.0);
        final var result = p1.subtract(p2);
        assertEquals(new Position(2, 2, 2.0d), result);
    }

    @Test
    public void testDistanceTo() {
        final var p1 = new Position(0, 0);
        final var p2 = new Position(3, 4);
        assertEquals(5.0d, p1.distanceTo(p2), 0.005d);
    }

    @Test
    public void testTouching() {
        final var p1 = new Position(0, 0);
        final var p2 = new Position(1, 1);
        assertTrue(p1.touching(p2));
    }

    @Test
    public void testToString() {
        final var p = new Position(1, 2, 3.0);
        assertEquals("(1, 2, 3.00)", p.toString());
    }

    @Test
    public void testEquals() {
        final var p1 = new Position(1, 2);
        final var p2 = new Position(1, 2);
        assertEquals(p1, p2);
    }

    @Test
    public void testHashCode() {
        final var p1 = new Position(1, 2);
        final var p2 = new Position(1, 2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}
