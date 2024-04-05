package habbo.rooms.components.objects;

import habbo.rooms.IRoomComponent;
import habbo.rooms.components.objects.items.IRoomItem;
import habbo.rooms.components.objects.items.floor.IFloorItem;
import habbo.rooms.components.objects.items.wall.IWallItem;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.SequencedCollection;
import java.util.function.Predicate;

public interface IRoomObjectManager extends IRoomComponent {
    public void addRoomItem(IRoomItem roomItem);
    public Collection<IRoomItem> getAllItems();

    public Collection<IFloorItem> getAllFloorItems();

    public Collection<IWallItem> getAllWallItems();

    public SequencedCollection<IRoomItem> getItemsWhere(Predicate<IRoomItem> predicate);

    public int getVirtualIdForItemId(long itemId);

    public @Nullable IRoomItem getItemByVirtualId(int virtualId);

    List<String> getFurnitureOwners();
}
