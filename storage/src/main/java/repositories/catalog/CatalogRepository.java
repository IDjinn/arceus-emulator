package repositories.catalog;

import com.google.inject.Singleton;
import queries.catalog.CatalogQuery;
import repositories.ConnectionRepository;
import storage.repositories.catalog.ICatalogRepository;
import storage.results.IConnectionResultConsumer;

@Singleton
public class CatalogRepository extends ConnectionRepository implements ICatalogRepository {

    @Override
    public void getAllCatalogPages(IConnectionResultConsumer result) {
        this.select(CatalogQuery.SELECT_ALL_PAGES.get(), result);
    }

    @Override
    public void getAllCatalogItems(IConnectionResultConsumer result) {
        this.select(CatalogQuery.SELECT_ALL_ITEMS.get(), result);
    }
}
