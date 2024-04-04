package habbo.catalog.items;

import habbo.catalog.pages.ICatalogPage;
import storage.results.IConnectionResult;

import java.util.List;

public interface ICatalogFactory {
    public ICatalogItem createCatalogItem(IConnectionResult result) throws Exception;

    public ICatalogPage createCatalogPage(IConnectionResult result, List<ICatalogItem> items) throws Exception;
}
