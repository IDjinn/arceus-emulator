package habbo.catalog;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.catalog.item.CatalogItem;
import habbo.catalog.items.ICatalogFactory;
import habbo.catalog.items.ICatalogItem;
import habbo.catalog.page.layouts.DefaultCatalogPage;
import habbo.catalog.pages.ICatalogPage;
import habbo.furniture.IFurnitureManager;
import storage.results.IConnectionResult;

import java.util.HashMap;
import java.util.List;

@Singleton
public class CatalogFactory implements ICatalogFactory {
    private final HashMap<String, ICatalogPage> catalogPageTypes = new HashMap<String, ICatalogPage>();
    @Inject
    private IFurnitureManager furnitureManager;


    @Override
    public ICatalogItem createCatalogItem(IConnectionResult result) throws Exception {
        var item = new CatalogItem(this.furnitureManager);
        item.fill(result);
        return item;
    }

    @Override
    public ICatalogPage createCatalogPage(IConnectionResult result, List<ICatalogItem> items) throws Exception {
        var page = new DefaultCatalogPage();
        page.fill(result);
        for (var item : items) {
            page.getItems().put(item.getId(), item);
        }
        return page;
    }
}
