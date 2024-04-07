package habbo.habbos.factories;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import habbo.furniture.IFurniture;
import habbo.furniture.IFurnitureManager;
import habbo.furniture.extra.data.IExtraData;
import habbo.habbos.inventory.IHabboInventoryItem;
import habbo.rooms.components.objects.items.ILimitedData;
import storage.results.IConnectionResult;

@Singleton
public class HabboInventoryItemFactory implements IHabboInventoryItemFactory {
    @Inject
    private Injector injector;
    @Inject
    private IFurnitureManager furnitureManager;

    @Override
    public IHabboInventoryItem create(IConnectionResult data) throws Exception {
        var inventoryItem = new HabboInventoryItem();
        injector.injectMembers(inventoryItem);
        inventoryItem.fill(data);
        return inventoryItem;
    }

    @Override
    public IHabboInventoryItem create(int itemId, IFurniture furniture, IExtraData extraData, ILimitedData limitedData) throws Exception {
        var inventoryItem = new HabboInventoryItem(itemId, furniture, extraData, limitedData, null, 0);
        injector.injectMembers(inventoryItem);
        return inventoryItem;
    }
}
