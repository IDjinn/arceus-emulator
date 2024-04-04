package habbo.rooms.components.objects.items;

import habbo.rooms.IRoom;
import storage.results.IConnectionResult;

public interface IRoomItemFactory {
    IRoomItem create(IConnectionResult result, IRoom room) throws Exception;
}
