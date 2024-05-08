package habbo.catalog;

import com.google.inject.AbstractModule;
import habbo.catalog.item.CatalogItem;
import habbo.catalog.items.ICatalogFactory;
import habbo.catalog.items.ICatalogItem;

public class CatalogModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(ICatalogManager.class).to(CatalogManager.class);
        this.bind(ICatalogFactory.class).to(CatalogFactory.class);
        this.bind(ICatalogItem.class).to(CatalogItem.class);
//         bind(ICatalogPage.class).to(CatalogPage.class);
    }
}
