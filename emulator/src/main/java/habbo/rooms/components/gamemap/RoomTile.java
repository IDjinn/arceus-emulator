package habbo.rooms.components.gamemap;

public class RoomTile implements IRoomTile {
    private final int x;
    private final int y;
    private final int z;

    public RoomTile(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getZ() {
        return this.z;
    }
}
