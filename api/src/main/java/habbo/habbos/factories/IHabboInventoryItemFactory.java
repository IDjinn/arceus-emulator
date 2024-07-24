package habbo.habbos.factories;

import habbo.furniture.IFurniture;
import habbo.furniture.extra.data.IExtraData;
import habbo.habbos.IHabbo;
import habbo.habbos.inventory.IHabboInventoryItem;
import storage.results.IConnectionResult;

public interface IHabboInventoryItemFactory {
    IHabboInventoryItem create(IConnectionResult data, IHabbo habbo) throws Exception;

    IHabboInventoryItem create(IHabbo habbo, int itemId, IFurniture furniture, IExtraData extraData) throws Exception;
}
