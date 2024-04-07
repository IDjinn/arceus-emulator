package habbo.habbos.factories;

import habbo.furniture.IFurniture;
import habbo.furniture.extra.data.IExtraData;
import habbo.habbos.inventory.IHabboInventoryItem;
import habbo.rooms.components.objects.items.ILimitedData;
import storage.results.IConnectionResult;

public interface IHabboInventoryItemFactory {
    public IHabboInventoryItem create(IConnectionResult data) throws Exception;

    public IHabboInventoryItem create(int itemId, IFurniture furniture, IExtraData extraData, ILimitedData limitedData) throws Exception;
}
