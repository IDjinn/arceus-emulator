package habbo.rooms.components.objects;

import habbo.rooms.IRoomComponent;
import habbo.rooms.components.objects.items.IRoomItem;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.SequencedCollection;
import java.util.function.Predicate;

public interface IObjectManager extends IRoomComponent {
    public Collection<IRoomItem> getAllItems();

    public SequencedCollection<IRoomItem> getItemsWhere(Predicate<IRoomItem> predicate);

    public long getVirtualIdForItemId(long itemId);

    public @Nullable IRoomItem getItemByVirtualId(long virtualId);
}
