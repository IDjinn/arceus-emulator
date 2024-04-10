package habbo.rooms.components.objects.items;

import habbo.furniture.extra.data.IExtraData;
import habbo.furniture.extra.data.LegacyExtraData;
import utils.pathfinder.Position;

public class RoomItemData implements IRoomItemData {
    private final int id;
    private final int itemId;
    private int ownerId;
    private IExtraData data;
    private int rotation;
    private Position floorPosition;
    private String wallPosition;

    public RoomItemData(int id, int itemId, int ownerId, Position position, int rotation, IExtraData data,
                        String wallPosition) {
        this.id = id;
        this.itemId = itemId;
        this.ownerId = ownerId;
        this.data = data;

        this.rotation = rotation;
        this.floorPosition = position;

        this.wallPosition = wallPosition;

    }

    public int getId() {
        return id;
    }

    @Override
    public int getFurnitureId() {
        return this.itemId;
    }

    public int getOwnerId() {
        return ownerId;
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
        return data;
    }

    @Override
    public void setData(IExtraData data) {
        this.data = data;
    }

    @Override
    public int getIntData() {
        if (this.data instanceof LegacyExtraData legacyExtraData)
            legacyExtraData.getStateValue();
        return 0;
    }
}
