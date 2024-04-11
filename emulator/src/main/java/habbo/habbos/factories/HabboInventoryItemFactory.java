package habbo.habbos.factories;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import habbo.furniture.IFurniture;
import habbo.furniture.IFurnitureManager;
import habbo.furniture.extra.data.IExtraData;
import habbo.habbos.IHabbo;
import habbo.habbos.inventory.IHabboInventoryItem;
import storage.results.IConnectionResult;

@Singleton
public class HabboInventoryItemFactory implements IHabboInventoryItemFactory {
    @Inject
    private Injector injector;
    @Inject
    private IFurnitureManager furnitureManager;

    @Override
    public IHabboInventoryItem create(IConnectionResult data, IHabbo habbo) throws Exception {
        var inventoryItem = new HabboInventoryItem(habbo);
        this.injector.injectMembers(inventoryItem);
        inventoryItem.fill(data);
        return inventoryItem;
    }

    @Override
    public IHabboInventoryItem create(IHabbo habbo, int itemId, IFurniture furniture, IExtraData extraData) throws Exception {
        var inventoryItem = new HabboInventoryItem(habbo, itemId, furniture, extraData, "", 0);
        this.injector.injectMembers(inventoryItem);
        return inventoryItem;
    }
}
