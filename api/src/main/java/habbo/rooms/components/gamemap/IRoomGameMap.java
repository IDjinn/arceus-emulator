package habbo.rooms.components.gamemap;

import habbo.rooms.IRoomComponent;
import utils.pathfinder.Position;

public interface IRoomGameMap extends IRoomComponent {
    IRoomTile[][] getMap();

    IRoomTile getTile(int x, int y);

    int getMaxX();

    int getMaxY();

    int getMaxZ();

    int getMapSize();

    String getModelMap();

    boolean isValidCoordinate(Position position);

    boolean isValidMovement(Position from, Position to, Position goal);

    IRoomTile getTile(final Position position);
}
