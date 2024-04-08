package storage.repositories.catalog;

import storage.repositories.IConnectionRepository;
import storage.results.IConnectionResultConsumer;

public interface ICatalogRepository extends IConnectionRepository {

    public void getAllCatalogPages(IConnectionResultConsumer result);

    public void getAllCatalogItems(IConnectionResultConsumer result);
//    void purchaseItems(List<ICatalogItem> catalogItems, Consumer<List<Long>> idConsumer,
//                       int userId,
//                       int furniId,
//                       IExtraData extraData
//    );
}
