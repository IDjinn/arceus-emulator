package habbo.catalog;

import core.IHotelService;
import habbo.catalog.items.ICatalogItem;
import habbo.catalog.pages.ICatalogPage;
import habbo.habbos.IHabbo;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public interface ICatalogManager extends IHotelService {
    public HashMap<Integer, ICatalogItem> getCatalogItems();

    public HashMap<Integer, ICatalogPage> getCatalogPages();

    public List<ICatalogPage> getCatalogPagesForHabbo(int parentId, IHabbo habbo);

    public @Nullable ICatalogPage getCatalogPageForHabbo(int pageId, IHabbo habbo);

    public ICatalogPurchaseHandler getPurchaseHandlerForItem(ICatalogItem item);
}
