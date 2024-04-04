package habbo.catalog;

import com.google.inject.Inject;
import habbo.catalog.items.ICatalogItem;
import habbo.furniture.IFurnitureManager;
import habbo.habbos.IHabbo;
import packets.outgoing.catalog.PurchaseOkComposer;

public class DefaultCatalogPurchaseHandler implements ICatalogPurchaseHandler {
    @Inject
    private IFurnitureManager furnitureManager;

    @Override
    public boolean canPurchase(IHabbo habbo, ICatalogItem item, String extraData, int amount) {
        return amount > 0 && amount <= 100;
    }

    @Override
    public boolean purchase(IHabbo habbo, ICatalogItem item, String extraData, int amount) {
        var furniture = furnitureManager.get(Integer.parseInt(item.getItemId())); // TODO: IT AS PROPERTY INSTEAD MULTIPLE CALLS
        habbo.getClient().sendMessage(new PurchaseOkComposer(item, furniture));
        return false;
    }
}
