package habbo.rooms.components.gamemap;

import com.google.inject.Inject;
import habbo.rooms.IRoom;
import habbo.rooms.IRoomManager;
import habbo.rooms.components.objects.items.floor.IFloorFloorItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import packets.outgoing.rooms.gamemap.UpdateStackHeightComposer;
import stormpot.Pool;
import stormpot.Timeout;
import utils.pathfinder.Position;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RoomGameMap implements IRoomGameMap {
    private final Logger logger = LogManager.getLogger();
    @Inject
    private IRoomManager roomManager;


    private IRoom room;
    private IRoomTile[][] tiles;
    private int mapSize;


    private final Pool<TileMetadata> tileMetadataPool = Pool
            .from(new TileMetadataAllocator())
            .setSize(1000)
            .setThreadFactory(Thread.ofVirtual().factory())
            .build();

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
            var maxX = map[0].length();
            var maxY = map.length;
            this.tiles = new IRoomTile[maxX][maxY];

            for (int x = 0; x < maxX; x++) {
                for (int y = 0; y < maxY; y++) {
                    var height = map_height_lookup(Character.toUpperCase(map[y].charAt(x)));
                    this.tiles[x][y] = new RoomTile(new Position(x, y, height));
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
    public List<ITileMetadata> getMetadataAt(final int x, final int y, final double objectHeight) {
        final var tile = this.getTile(x, y);
        final var result = new LinkedList<ITileMetadata>();
        // sort is 0 -> infinity
        for (int i = 0; i < tile.getMetadata().size(); i++) {
            var currentMetadata = tile.getMetadata().get(i);
            if (currentMetadata.getHeight().isEmpty())
                continue;

            final var currentTopZ = currentMetadata.getHeight().get();
            if (currentTopZ == Short.MAX_VALUE)
                continue;

            var bottomMetadata = i - 1 >= 0 ? tile.getMetadata().get(i - 1) : null;
            var topMetadata = tile.getMetadata().size() > i + 1 ? tile.getMetadata().get(i + 1) : null;
            if (topMetadata == null || topMetadata.getHeight().isEmpty()) {
                result.add(currentMetadata);
                return result;
            }

            var nextBottomZ = topMetadata.getHeight().get();

            if (currentTopZ + objectHeight <= nextBottomZ) {
                result.add(currentMetadata);
            }
        }


        return tile.getMetadata();
    }

    @Override
    public void updateTiles(final IRoomTile... tiles) {
        for (final var tile : tiles) {
            this.updateTile(tile);
        }
    }

    @Override
    public void updateTiles() {
        for (final var mapRow : this.getMap()) {
            for (final var tile : mapRow) {
                this.updateTile(tile);
            }
        }
    }

    @Override
    public void sendUpdate(final IRoomTile tile) {
        this.getRoom().broadcastMessage(new UpdateStackHeightComposer(tile));
    }

    @Override
    public void sendUpdate(final IRoomTile... tiles) {
        this.getRoom().broadcastMessage(new UpdateStackHeightComposer(List.of(tiles)));
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
        return this.tiles.length;
    }

    @Override
    public int getMaxY() {
        return this.tiles[0].length;
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
    public boolean isValidCoordinate(final Position position) {
        return this.isValidCoordinate(position.getX(), position.getY());
    }

    @Override
    public boolean isValidCoordinate(final int x, final int y) {
        return x >= 0
                && x < this.getMaxX()
                && y >= 0
                && y < this.getMaxY()
                ;
    }

    @Override
    public boolean isValidMovement(final Position from, final Position to, final Position goal) {
        final var topItem = this.getRoom().getObjectManager().getTopFloorItemAt(to, -1);
        return topItem.map(IFloorFloorItem::canWalk).orElse(true);
    }

    @Override
    public IRoomTile getTile(final Position position) {
        return this.getTile(position.getX(), position.getY());
    }

    @Override
    public List<ITileMetadata> getMetadataAt(final Position position, final double objectHeight) {
        return this.getMetadataAt(position.getX(), position.getY(), objectHeight);
    }

    @Override
    public void updateTile(final IRoomTile tile) {
        for (var metadata : tile.getMetadata()) {
            metadata.release();
        }

        tile.getMetadata().clear();
        try {
            var metadata = this.tileMetadataPool.claim(new Timeout(1, TimeUnit.SECONDS));
            metadata.setRoomTile(tile);
            metadata.setWalkableHeight(tile.getPosition().getZ());
            tile.getMetadata().add(metadata);
        } catch (Exception e) {
            this.logger.error("error creating metadata room {} tile {}:{}", this.getRoom().getData().getId(),
                    tile.getX(), tile.getY(), e);
        }

        final var itemsAt = this.getRoom().getObjectManager().getAllFloorItemsSortedAt(tile.getPosition());
        for (final var item : itemsAt) {
            try {
                var metadata = this.tileMetadataPool.claim(new Timeout(1, TimeUnit.SECONDS));
                metadata.setRoomTile(tile);
                metadata.setItem(item);

                item.getWalkableHeight().ifPresent(metadata::setWalkableHeight);
                item.getSitHeight().ifPresent(metadata::setSitHeight);
                item.getLayHeight().ifPresent(metadata::setLayHeight);
                item.getStackHeight().ifPresent(metadata::setStackHeight);
                tile.getMetadata().add(metadata);
            } catch (Exception e) {
                this.logger.error("error creating metadata room {} tile {}:{}", this.getRoom().getData().getId(),
                        tile.getX(), tile.getY(), e);
            }
        }
    }
}
