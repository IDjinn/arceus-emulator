package habbo.rooms.components.objects.items;

import habbo.furniture.extra.data.IExtraData;
import habbo.rooms.IRoom;
import org.jetbrains.annotations.NotNull;
import storage.results.IConnectionResult;
import utils.pathfinder.Position;

public interface IRoomItemFactory {

    boolean registerInteraction(final @NotNull String name, Class<? extends IRoomItem> clazz);

    boolean unregisterInteraction(final @NotNull String name);
    
    IRoomItem create(IConnectionResult result, IRoom room) throws Exception;

    IRoomItem create(IRoomItemData itemData, IRoom room);

    IRoomItemData createItemData(
            final int id,
            final int furnitureId,
            final int ownerId,
            final Position position,
            final int rotation,
            final IExtraData extraData,
            final String wiredData
    );

    IRoomItemData createItemData(
            int id,
            int furnitureId,
            int ownerId,
            String wallPosition,
            IExtraData extraData
    );
}
