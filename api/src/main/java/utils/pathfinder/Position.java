package utils.pathfinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Position {
    public static final int NORTH = 0;
    public static final int NORTH_EAST = 1;
    public static final int EAST = 2;
    public static final int SOUTH_EAST = 3;
    public static final int SOUTH = 4;
    public static final int SOUTH_WEST = 5;
    public static final int WEST = 6;
    public static final int NORTH_WEST = 7;

    private int x;
    private int y;
    private double z;
    private int flag = -1;
    private int prevX;
    private int prevY;

    public Position(int x, int y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position(Position old) {
        this.x = old.getX();
        this.y = old.getY();
        this.z = old.getZ();
    }

    public Position() {
        this.x = 0;
        this.y = 0;
        this.z = 0d;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.z = 0d;
    }

    public static int calculateRotation(Position from, Position to) {
        return calculateRotation(from.x, from.y, to.x, to.y, false);
    }

    public static int calculateRotation(int x, int y, int newX, int newY, boolean reversed) {
        int rotation = 0;

        if (x > newX && y > newY) {
            rotation = 7;
        } else if (x < newX && y < newY) {
            rotation = 3;
        } else if (x > newX && y < newY) {
            rotation = 5;
        } else if (x < newX && y > newY) {
            rotation = 1;
        } else if (x > newX) {
            rotation = 6;
        } else if (x < newX) {
            rotation = 2;
        } else if (y < newY) {
            rotation = 4;
        } else if (y > newY) {
            rotation = 0;
        }

        if (reversed) {
            rotation = (rotation + 4) % 8;
        }

        return rotation;
    }

    public static int getInvertedRotation(int currentRotation) {
        return switch (currentRotation) {
            case NORTH_EAST -> SOUTH_WEST;
            case NORTH_WEST -> SOUTH_EAST;
            case SOUTH_WEST -> NORTH_EAST;
            case SOUTH_EAST -> NORTH_WEST;
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
            default -> currentRotation;
        };
    }

    public static Position calculatePosition(int x, int y, int angle, boolean isReversed, int distance) {
        var newX = x;
        var newY = y;
        switch (angle) {
            case NORTH -> newY += isReversed ? distance : -distance;
            case NORTH_EAST -> {
                newX += isReversed ? -distance : distance;
                newY += isReversed ? distance : -distance;
            }
            case EAST -> newX += isReversed ? -distance : distance;
            case SOUTH_EAST -> {
                newX += isReversed ? -distance : distance;
                newY += isReversed ? -distance : distance;
            }
            case SOUTH -> newY += isReversed ? -distance : distance;
            case SOUTH_WEST -> {
                newX += isReversed ? distance : -distance;
                newY += isReversed ? -distance : distance;
            }
            case WEST -> newX += isReversed ? distance : -distance;
            case NORTH_WEST -> {
                newX += isReversed ? distance : -distance;
                newY += isReversed ? distance : -distance;
            }
        }
        return new Position(newX, newY);
    }

    public static List<Position> makeSquareInclusive(Position p1, Position p2) {
        final int lowerX = Math.min(p1.getX(), p2.getX());
        final int lowerY = Math.min(p1.getY(), p2.getY());
        final int higherX = Math.max(p1.getX(), p2.getX());
        final int higherY = Math.max(p1.getY(), p2.getY());
        final List<Position> positions = new ArrayList<>((higherX - lowerX + 1) * (higherY - lowerY + 1));

        for (int x = lowerX; x <= higherX; x++) {
            for (int y = lowerY; y <= higherY; y++) {
                positions.add(new Position(x, y));
            }
        }

        return positions;
    }

    public int getLocalCost(Position start, Position goal) {
        if (this.x == start.getX() && this.y == start.getY()) return Integer.MAX_VALUE;
        return Math.abs(this.x - goal.x) + Math.abs(this.y - goal.y);
    }

    public Position add(Position other) {
        return new Position(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Position subtract(Position other) {
        return new Position(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public int getDistanceSquared(Position point) {
        int dx = this.x - point.x;
        int dy = this.y - point.y;
        return dx * dx + dy * dy;
    }

    public Position squareInFront(int angle) {
        return calculatePosition(this.x, this.y, angle, false, 1);
    }

    public Position squareInFront(int angle, int distance) {
        return calculatePosition(this.x, this.y, angle, false, distance);
    }

    public Position squareBehind(int angle) {
        return calculatePosition(this.x, this.y, angle, true, 1);
    }

    public double distanceTo(Position pos) {
        return Math.sqrt(Math.pow(this.x - pos.x, 2) + Math.pow(this.y - pos.y, 2));
    }

    public boolean touching(Position pos) {
        int dx = Math.abs(this.x - pos.x);
        int dy = Math.abs(this.y - pos.y);
        return dx <= 1 && dy <= 1;
    }

    public Position copy() {
        return new Position(this.x, this.y, this.z);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Position) {
            Position other = (Position) o;
            return this.x == other.x && this.y == other.y;
        }
        return false;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.prevX = this.x;
        this.x = x;
    }

    public int getPrevX() {
        return this.prevX;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.prevY = this.y;
        this.y = y;
    }

    public int getPrevY() {
        return this.prevY;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void incrementX(int amount) {
        this.x += amount;
    }

    public void incrementY(int amount) {
        this.y += amount;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "(%d, %d, %.2f)", this.x, this.y, this.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    public int getFlag() {
        return this.flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
