package habbohotel.rooms.components.gamemap;

import habbohotel.rooms.IRoom;

public class RoomGameMapComponent implements IRoomGameMapComponent {
    private final String MODEL_A = "xxxxxxxxxxxx\n" +
            "xxxx00000000\n" +
            "xxxx00000000\n" +
            "xxxx00000000\n" +
            "xxxx00000000\n" +
            "xxx000000000\n" +
            "xxxx00000000\n" +
            "xxxx00000000\n" +
            "xxxx00000000\n" +
            "xxxx00000000\n" +
            "xxxx00000000\n" +
            "xxxx00000000\n" +
            "xxxx00000000\n" +
            "xxxx00000000\n" +
            "xxxxxxxxxxxx\n" +
            "xxxxxxxxxxxx".replaceAll("\r", "");
    private final IRoom room;
    private IRoomTile[][] tiles;
    private int mapSize;

    public RoomGameMapComponent(IRoom room) {
        this.room = room;
    }

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
        return room;
    }

    @Override
    public void init() {
        try {
            var map = MODEL_A.split("\n");
            var modelWidth = map.length;
            var modelHeight = map[0].length();
            tiles = new IRoomTile[modelWidth][modelHeight];

            for (int x = 0; x < modelWidth; x++) {
                for (int y = 0; y < modelHeight; y++) {
                    tiles[x][y] = new RoomTile(x, y, map_height_lookup(Character.toUpperCase(map[x].charAt(y))));
                    mapSize++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        return tiles;
    }

    @Override
    public IRoomTile getTile(int x, int y) {
        return tiles[x][y];
    }

    @Override
    public int getMaxX() {
        return tiles.length;
    }

    @Override
    public int getMaxY() {
        return tiles[0].length;
    }

    @Override
    public int getMaxZ() {
        return 40;
    }

    @Override
    public int getMapSize() {
        return mapSize;
    }

    @Override
    public String getModelMap() { // TODO habbo client does use \r instead \n
        return MODEL_A.replaceAll("\n", "\r");
    }
}
