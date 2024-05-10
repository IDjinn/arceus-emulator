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

    List<ITileMetadata> getMetadataAt(final Position position, double objectHeight);

    List<ITileMetadata> getMetadataAt(final int x, final int y, double objectHeight);

    void updateTile(final IRoomTile tile);

    void updateTiles();

    void updateTiles(final IRoomTile... tiles);

    void sendUpdate(final IRoomTile tile);

    void sendUpdate(final IRoomTile... tiles);
}
