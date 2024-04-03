package habbo.rooms.components.gamemap;

import habbo.rooms.IRoomComponent;
import utils.Position;

public interface IGameMap extends IRoomComponent {
    public IRoomTile[][] getMap();

    public IRoomTile getTile(int x, int y);

    public int getMaxX();

    public int getMaxY();

    public int getMaxZ();

    public int getMapSize();

    public String getModelMap();

    boolean isValidCoordinate(Position neighborPosition);
}
