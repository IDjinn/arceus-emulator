package habbo.rooms.components.objects.items;

import utils.Position;

public class RoomItemData implements IRoomItemData {
    private final long id;
    private final int itemId;
    private final ILimitedData limitedEdition;
    private int ownerId;
    private String data;
    private int rotation;
    private Position floorPosition;
    private String wallPosition;

    public RoomItemData(long id, int itemId, int ownerId, Position position, int rotation, String data, String wallPosition, ILimitedData limitedEditionItem) {
        this.id = id;
        this.itemId = itemId;
        this.ownerId = ownerId;
        this.data = data;

        this.rotation = rotation;
        this.floorPosition = position;

        this.wallPosition = wallPosition;

        this.limitedEdition = limitedEditionItem;
    }

    public long getId() {
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
    public void setOwnerId(int userId) {
        this.ownerId = userId;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public void setData(int data) {
        this.data = String.valueOf(data);
    }

    @Override
    public int getIntData() {
        try {
            return Integer.parseInt(this.data);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void decrementData(int i) {
        this.data = String.valueOf(Integer.parseInt(this.data) - 1);
    }

    @Override
    public ILimitedData getLimitedEdition() {
        return limitedEdition;
    }
}
