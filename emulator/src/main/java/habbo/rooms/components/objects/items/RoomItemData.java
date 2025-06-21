package habbo.rooms.components.objects.items;

import habbo.furniture.extra.data.IExtraData;
import lombok.Getter;
import lombok.Setter;
import utils.pathfinder.Position;

public class RoomItemData implements IRoomItemData {
    @Getter
    private final int id;
    private final int itemId;
    @Getter
    private final int ownerId;
    private IExtraData data;
    private int rotation;
    private Position floorPosition;
    private String wallPosition;
    @Getter
    @Setter
    private String wiredData;

    public RoomItemData(
            final int id,
            final int itemId,
            final int ownerId,
            final Position position,
            final int rotation,
            final IExtraData data,
            final String wallPosition,
            final String wiredData
    ) {
        this.id = id;
        this.itemId = itemId;
        this.ownerId = ownerId;
        this.data = data;

        this.rotation = rotation;
        this.floorPosition = position;

        this.wallPosition = wallPosition;

        this.wiredData = wiredData;
    }

    @Override
    public int getFurnitureId() {
        return this.itemId;
    }

    @Override
    public Position getPosition() {
        return this.floorPosition;
    }

    @Override
    public void setPosition(Position position) {
        this.floorPosition = position;
    }

    @Override
    public String getWallPosition() {
        return this.wallPosition;
    }

    @Override
    public void setWallPosition(String wallPosition) {
        this.wallPosition = wallPosition;
    }

    @Override
    public int getRotation() {
        return this.rotation;
    }

    @Override
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    @Override
    public IExtraData getData() {
        return this.data;
    }

    @Override
    public void setExtraData(IExtraData data) {
        this.data = data;
    }

}
