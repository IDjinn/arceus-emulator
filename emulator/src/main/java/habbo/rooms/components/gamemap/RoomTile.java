package habbo.rooms.components.gamemap;

import utils.pathfinder.Position;

public class RoomTile implements IRoomTile {
    private final Position position;

    public RoomTile(Position position) {
        this.position = position;
    }

    @Override
    public int getX() {
        return this.position.getX();
    }

    @Override
    public int getY() {
        return this.position.getY();
    }

    @Override
    public int getZ() {
        return (int) this.position.getZ();
    }

    @Override
    public Position getPosition() {
        return this.position;
    }
}
