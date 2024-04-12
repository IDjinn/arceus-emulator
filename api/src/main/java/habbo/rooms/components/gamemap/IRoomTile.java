package habbo.rooms.components.gamemap;

import utils.pathfinder.Position;

import java.util.List;

public interface IRoomTile {
    int getX();

    int getY();

    int getZ();

    Position getPosition();

    TileState getState();

    List<ITileMetadata> getMetadata();
}
