package utils.pathfinder;

import java.util.ArrayList;
import java.util.List;
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

    public static final int[] COLLIDE_TILES = new int[]{
            NORTH, EAST, SOUTH, WEST
    };

    public static final int[] DIAG_TILES = new int[]{
            NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST
    };
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

    public static String validateWallPosition(String position) {
        try {
            String[] data = position.split(" ");
            if (data[2].equals("l") || data[2].equals("r")) {
                String[] width = data[0].substring(3).split(",");
                int widthX = Integer.parseInt(width[0]);
                int widthY = Integer.parseInt(width[1]);
//                if (widthX < 0 || widthY < 0 || widthX > 200 || widthY > 200)
//                    return null;

                String[] length = data[1].substring(2).split(",");
                int lengthX = Integer.parseInt(length[0]);
                int lengthY = Integer.parseInt(length[1]);
//                if (lengthX < 0 || lengthY < 0 || lengthX > 200 || lengthY > 200)
//                    return null;

                return STR."\{STR."\{STR."\{STR."\{":w=" + widthX + "," + widthY} "}l=" + lengthX}," + lengthY} " + data[2];
            }
        } catch (Exception ignored) {

        }

        return null;
    }

    public static int calculateRotation(Position from, Position to) {
        return calculateRotation(from.x, from.y, to.x, to.y, false);
    }

    public static int calculateRotation(int x, int y, int newX, int newY, boolean reversed) {
        int rotation = 0;

        if (x > newX && y > newY)
            rotation = 7;
        else if (x < newX && y < newY)
            rotation = 3;
        else if (x > newX && y < newY)
            rotation = 5;
        else if (x < newX && y > newY)
            rotation = 1;
        else if (x > newX)
            rotation = 6;
        else if (x < newX)
            rotation = 2;
        else if (y < newY)
            rotation = 4;
        else if (y > newY)
            rotation = 0;

        if (reversed) {
            if (rotation > 3) {
                rotation = rotation - 4;
            } else {
                rotation = rotation + 4;
            }
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
        switch (angle) {
            case 0:
                if (!isReversed)
                    y -= distance;
                else
                    y += distance;
                break;

            case 1:
                if (!isReversed) {
                    x += distance;
                    y -= distance;
                } else {
                    x -= distance;
                    y += distance;
                }
                break;

            case 2:
                if (!isReversed)
                    x += distance;
                else
                    x -= distance;
                break;

            case 3:
                if (!isReversed) {
                    x += distance;
                    y += distance;
                } else {
                    x -= distance;
                    y -= distance;
                }
                break;

            case 4:
                if (!isReversed)
                    y += distance;
                else
                    y -= distance;
                break;

            case 5:
                if (!isReversed) {
                    x -= distance;
                    y += distance;
                } else {
                    x++;
                    y--;
                }
                break;

            case 6:
                if (!isReversed)
                    x -= distance;
                else
                    x += distance;
                break;

            case 7:
                if (!isReversed) {
                    x -= distance;
                    y -= distance;
                } else {
                    x += distance;
                    y += distance;
                }
                break;
        }

        return new Position(x, y);
    }

    public static List<Position> makeSquareInclusive(Position p1, Position p2) {
        final int lowerX = Math.min(p1.getX(), p2.getX());
        final int lowerY = Math.min(p1.getY(), p2.getY());
        final int higherX = Math.max(p1.getX(), p2.getX());
        final int higherY = Math.max(p1.getY(), p2.getY());
        final List<Position> positions = new ArrayList<>((higherX - lowerX) * (higherY - lowerY));

        for (int x = lowerX; x <= higherX; x++) {
            for (int y = lowerY; y <= higherY; y++) {
                positions.add(new Position(x, y));
            }
        }

        return positions;
    }

    public final int getLocalCost(Position start, Position goal) {
        if (this.x == start.getX() && this.y == start.getY()) {
            return 999999;
        }

        return Math.abs(this.x - goal.x) + Math.abs(this.y - goal.y);
    }

    public Position add(Position other) {
        return new Position(other.getX() + getX(), other.getY() + getY(), other.getZ() + getZ());
    }

    public Position subtract(Position other) {
        return new Position(other.getX() - getX(), other.getY() - getY(), other.getZ() - getZ());
    }

    public int getDistanceSquared(Position point) {
        int dx = this.getX() - point.getX();
        int dy = this.getY() - point.getY();

        return Math.abs(this.x - point.getX()) + Math.abs(this.y - point.getY());
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
        return Math.sqrt(Math.pow((this.getX() - pos.getX()), 2) + Math.pow((this.getY() - pos.getY()), 2));
    }

    public boolean touching(Position pos) {
        if (!(Math.abs(this.getX() - pos.getX()) > 1 || Math.abs(this.getY() - pos.getY()) > 1)) {
            return true;
        }

        return this.getX() == pos.getX() && this.getY() == pos.getY();

    }

    public Position copy() {
        return new Position(this.x, this.y, this.z);
    }

    @Override
    public String toString() {
        return STR."\{STR."\{"(" + this.getX() + ", " + this.getY()}, " + this.getZ()})";
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
    public boolean equals(Object o) {
        if (o instanceof Position) {
            return ((Position) o).getX() == this.getX() && ((Position) o).getY() == this.getY();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z);
    }

    public int getFlag() {
        return this.flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
