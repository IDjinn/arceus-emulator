package habbo.rooms.components.gamemap;

import com.google.inject.Inject;
import habbo.rooms.IRoom;
import habbo.rooms.IRoomManager;
import habbo.rooms.components.objects.items.floor.IFloorItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.pathfinder.Position;

public class RoomGameMap implements IRoomGameMap {
    private final Logger logger = LogManager.getLogger();
    @Inject
    private IRoomManager roomManager;
    private IRoom room;
    private IRoomTile[][] tiles;
    private int mapSize;

    private static int map_height_lookup(char tile) {
        return switch (tile) {
            case '0' -> 0;
            case '1' -> 1;
            case '2' -> 2;
            case '3' -> 3;
            case '4' -> 4;
            case '5' -> 5;
            case '6' -> 6;
            case '7' -> 7;
            case '8' -> 8;
            case '9' -> 9;
            case 'A' -> 10;
            case 'B' -> 11;
            case 'C' -> 12;
            case 'D' -> 13;
            case 'E' -> 14;
            case 'F' -> 15;
            case 'G' -> 16;
            case 'H' -> 17;
            case 'I' -> 18;
            case 'J' -> 19;
            case 'K' -> 20;
            case 'L' -> 21;
            case 'M' -> 22;
            case 'N' -> 23;
            case 'O' -> 24;
            case 'P' -> 25;
            case 'Q' -> 26;
            case 'R' -> 27;
            case 'S' -> 28;
            case 'T' -> 29;
            case 'U' -> 30;
            case 'V' -> 31;
            case 'W' -> 32;

            case 'X' -> Short.MAX_VALUE;
            default -> throw new IllegalArgumentException(STR."Invalid character: \{tile}");
        };
    }

    @Override
    public IRoom getRoom() {
        return this.room;
    }

    @Override
    public void init(IRoom room) {
        this.room = room;
        if (this.getRoom().getModel() == null)
            throw new IllegalArgumentException(STR."invalid room model \{this.getRoom().getData().getModelName()}");
        
        try {
            var map = this.getRoom().getModel().getHeightMap().split("\n");
            var modelWidth = map.length;
            var modelHeight = map[0].length();
            this.tiles = new IRoomTile[modelWidth][modelHeight];

            for (int x = 0; x < modelWidth; x++) {
                for (int y = 0; y < modelHeight; y++) {
                    this.tiles[x][y] = new RoomTile(new Position(x, y, map_height_lookup(Character.toUpperCase(map[x].charAt(y)))));
                    this.mapSize++;
                }
            }
        } catch (Exception e) {
            this.logger.error(e);
        }
    }

    @Override
    public void onRoomLoaded() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public IRoomTile[][] getMap() {
        return this.tiles;
    }

    @Override
    public IRoomTile getTile(int x, int y) {
        return this.tiles[x][y];
    }

    @Override
    public int getMaxX() {
        return this.tiles[0].length;
    }

    @Override
    public int getMaxY() {
        return this.tiles.length;
    }

    @Override
    public int getMaxZ() {
        return 40;
    }

    @Override
    public int getMapSize() {
        return this.mapSize;
    }

    @Override
    public String getModelMap() { // TODO habbo client does use \r instead \n
        return this.getRoom().getModel().getHeightMap().replaceAll("\n", "\r");
    }

    @Override
    public boolean isValidCoordinate(Position position) {
        return position.getX() >= 0 && position.getX() < getMaxX() &&
                position.getY() >= 0 && position.getY() < getMaxY();
    }

    @Override
    public boolean isValidMovement(final Position from, final Position to, final Position goal) {
        final var topItem = this.getRoom().getObjectManager().getTopFloorItemAt(to, -1);
        return topItem.map(IFloorItem::canWalk).orElse(true);

    }
}
