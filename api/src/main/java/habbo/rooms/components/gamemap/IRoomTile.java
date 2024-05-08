package habbo.rooms.components.gamemap;

import utils.pathfinder.Position;

import java.util.List;
import java.util.Optional;

public interface IRoomTile {
    int getX();

    int getY();

    int getZ();

    Position getPosition();

    TileState getState();

    List<ITileMetadata> getMetadata();

    Optional<Double> getRelativeMapHeight();
}
