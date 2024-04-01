package habbohotel.rooms.components.gamemap;

import habbohotel.rooms.IRoomComponent;

public interface IRoomGameMapComponent extends IRoomComponent {
    public IRoomTile[][] getMap();

    public IRoomTile getTile(int x, int y);

    public int getMaxX();

    public int getMaxY();

    public int getMaxZ();

    public int getMapSize();

    public String getModelMap();

}
