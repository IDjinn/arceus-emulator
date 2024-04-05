package habbo.habbos.factories;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import habbo.habbos.inventory.IHabboInventoryItem;
import storage.results.IConnectionResult;

@Singleton
public class HabboInventoryItemFactory implements IHabboInventoryItemFactory {
    @Inject
    private Injector injector;

    @Override
    public IHabboInventoryItem create(IConnectionResult data) throws Exception {
        var inventoryItem = new HabboInventoryItem();
        injector.injectMembers(inventoryItem);
        inventoryItem.fill(data);
        return inventoryItem;
    }
}
