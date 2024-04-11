package habbo.catalog.items;

import habbo.catalog.pages.ICatalogPage;
import storage.results.IConnectionResult;

import java.util.List;

public interface ICatalogFactory {
    ICatalogItem createCatalogItem(IConnectionResult result) throws Exception;

    ICatalogPage createCatalogPage(IConnectionResult result, List<ICatalogItem> items) throws Exception;
}
