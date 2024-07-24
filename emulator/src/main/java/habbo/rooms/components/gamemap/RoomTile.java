package habbo.rooms.components.gamemap;

import utils.pathfinder.Position;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class RoomTile implements IRoomTile {
    private final Position position;
    private final List<ITileMetadata> metaData;

    public RoomTile(final Position position) {
        this.position = position;
        this.metaData = new LinkedList<>();
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

    @Override
    public TileState getState() {
        return TileState.Open;
    }

    @Override
    public List<ITileMetadata> getMetadata() {
        return this.metaData;
    }

    @Override
    public Optional<Double> getRelativeMapHeight() {
        final var topMetadata = this.getMetadata().getLast();
        return topMetadata.getHeight();
    }
}
