package habbo.catalog;

import core.IHotelService;
import habbo.catalog.items.ICatalogItem;
import habbo.catalog.pages.ICatalogPage;
import habbo.habbos.IHabbo;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public interface ICatalogManager extends IHotelService {
    Map<Integer, ICatalogItem> getCatalogItems();

    Map<Integer, ICatalogPage> getCatalogPages();

    List<ICatalogPage> getCatalogPagesForHabbo(int parentId, IHabbo habbo);

    @Nullable ICatalogPage getCatalogPageForHabbo(int pageId, IHabbo habbo);

    ICatalogPurchaseHandler getPurchaseHandlerForItem(ICatalogItem item);
}
