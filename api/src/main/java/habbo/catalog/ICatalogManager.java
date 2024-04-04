package habbo.catalog;

import core.IHotelService;
import habbo.catalog.items.ICatalogItem;
import habbo.catalog.pages.ICatalogPage;
import habbo.habbos.IHabbo;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface ICatalogManager extends IHotelService {
    public HashMap<Integer, ICatalogItem> getCatalogItems();

    public HashMap<Integer, ICatalogPage> getCatalogPages();

    public List<ICatalogPage> getCatalogPagesForHabbo(int parentId, IHabbo habbo);

    public Optional<ICatalogPage> getCatalogPageForHabbo(int pageId, IHabbo habbo);

}
