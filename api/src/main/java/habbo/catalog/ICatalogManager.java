package habbo.catalog;

import core.IHotelService;
import habbo.catalog.items.ICatalogItem;
import habbo.catalog.pages.ICatalogPage;
import habbo.habbos.IHabbo;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public interface ICatalogManager extends IHotelService {
    HashMap<Integer, ICatalogItem> getCatalogItems();

    HashMap<Integer, ICatalogPage> getCatalogPages();

    List<ICatalogPage> getCatalogPagesForHabbo(int parentId, IHabbo habbo);

    @Nullable ICatalogPage getCatalogPageForHabbo(int pageId, IHabbo habbo);

    ICatalogPurchaseHandler getPurchaseHandlerForItem(ICatalogItem item);
}
