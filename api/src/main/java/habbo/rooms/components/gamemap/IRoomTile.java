package habbo.rooms.components.gamemap;

import utils.pathfinder.Position;

public interface IRoomTile {
    int getX();

    int getY();

    int getZ();

    Position getPosition();
}
