package habbo.rooms.components.objects.items;

import habbo.furniture.extra.data.IExtraData;
import habbo.rooms.IRoom;
import storage.results.IConnectionResult;
import utils.Position;

public interface IRoomItemFactory {
    IRoomItem create(IConnectionResult result, IRoom room) throws Exception;

    public IRoomItem create(IRoomItemData itemData, IRoom room);

    public IRoomItemData createItemData(
            int id,
            int furnitureId,
            int ownerId,
            Position position,
            int rotation,
            IExtraData extraData
    );

    public IRoomItemData createItemData(
            int id,
            int furnitureId,
            int ownerId,
            String wallPosition,
            IExtraData extraData
    );
}
