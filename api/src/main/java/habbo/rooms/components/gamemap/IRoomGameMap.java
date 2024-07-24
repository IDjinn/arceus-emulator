package habbo.rooms.components.gamemap;

import habbo.rooms.IRoomComponent;
import utils.pathfinder.Position;

import java.util.List;

public interface IRoomGameMap extends IRoomComponent {
    IRoomTile[][] getMap();

    IRoomTile getTile(int x, int y);

    int getMaxX();

    int getMaxY();

    int getMaxZ();

    int getMapSize();

    String getModelMap();

    boolean isValidCoordinate(Position position);

    boolean isValidCoordinate(final int x, final int y);

    boolean isValidMovement(Position from, Position to, Position goal);

    IRoomTile getTile(final Position position);

    List<ITileMetadata> getMetadataAt(Position position, double objectHeight);

    void updateTile(final IRoomTile tile);

    void updateTiles();

    List<ITileMetadata> getMetadataAt(int x, int y, double objectHeight);

    void updateTiles(final IRoomTile... tiles);

    void sendUpdate(final IRoomTile tile);

    void sendUpdate(final IRoomTile... tiles);
}
