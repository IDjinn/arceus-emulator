package habbo.catalog;

import habbo.catalog.items.ICatalogItem;
import habbo.habbos.IHabbo;

public interface ICatalogPurchaseHandler {
    public boolean canPurchase(IHabbo habbo, ICatalogItem item, String extraData, int amount);

    public boolean purchase(IHabbo habbo, ICatalogItem item, String extraData, int amount);
}
