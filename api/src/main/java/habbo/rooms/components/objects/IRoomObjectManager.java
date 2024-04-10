package habbo.rooms.components.objects;

import habbo.habbos.IHabbo;
import habbo.habbos.inventory.IHabboInventoryItem;
import habbo.rooms.IRoomComponent;
import habbo.rooms.components.objects.items.IRoomItem;
import habbo.rooms.components.objects.items.floor.IFloorItem;
import habbo.rooms.components.objects.items.wall.IWallItem;
import utils.cycle.ICycle;
import utils.pathfinder.Position;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.SequencedCollection;
import java.util.function.Predicate;

public interface IRoomObjectManager extends IRoomComponent, ICycle {
    public void addRoomItem(IRoomItem roomItem);
    public Collection<IRoomItem> getAllItems();

    public Collection<IFloorItem> getAllFloorItems();

    public @Nullable IFloorItem getFloorItem(int itemId);

    public Collection<IFloorItem> getAllFloorItemsAt(final Position position);

    public Collection<IFloorItem> getAllFloorItemsAt(final Position position, int ignoreId);

    public Optional<IFloorItem> getTopFloorItemAt(final Position position, long ignoreId);

    public Collection<IWallItem> getAllWallItems();

    public SequencedCollection<IRoomItem> getItemsWhere(Predicate<IRoomItem> predicate);

    public int getVirtualIdForItem(IRoomItem item);

    public @Nullable IRoomItem getItemByVirtualId(int virtualId);

    List<String> getFurnitureOwners();

    void placeFloorItem(final IHabbo habbo, IHabboInventoryItem item, int x, int y, double z, int rotation);

    void placeWallItem(final IHabbo habbo, IHabboInventoryItem item, String wallPosition);

    void moveFloorItemTo(IHabbo habbo, IFloorItem item, Position position, Integer rotation);

    void moveWallItemTo(IHabbo habbo, IWallItem floorItem, String coordinates);
}
