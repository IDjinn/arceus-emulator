package storage.repositories.catalog;

import storage.repositories.IConnectionRepository;
import storage.results.IConnectionResultConsumer;

public interface ICatalogRepository extends IConnectionRepository {

    void getAllCatalogPages(IConnectionResultConsumer result);

    void getAllCatalogItems(IConnectionResultConsumer result);
}
