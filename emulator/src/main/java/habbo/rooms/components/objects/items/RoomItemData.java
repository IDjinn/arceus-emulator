package habbo.rooms.components.objects.items;

import habbo.furniture.extra.data.IExtraData;
import utils.pathfinder.Position;

public class RoomItemData implements IRoomItemData {
    private final int id;
    private final int itemId;
    private final int ownerId;
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
        return this.id;
    }

    @Override
    public int getFurnitureId() {
        return this.itemId;
    }

    public int getOwnerId() {
        return this.ownerId;
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
