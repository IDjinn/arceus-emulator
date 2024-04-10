package habbo.catalog;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.mysql.cj.util.StringUtils;
import habbo.catalog.handlers.TeleportPurchaseHandler;
import habbo.catalog.items.ICatalogFactory;
import habbo.catalog.items.ICatalogItem;
import habbo.catalog.pages.ICatalogPage;
import habbo.furniture.IFurnitureManager;
import habbo.habbos.IHabbo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import storage.repositories.catalog.ICatalogRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Singleton
public class CatalogManager implements ICatalogManager {
    private final IFurnitureManager furnitureManager;
    private final ICatalogRepository catalogRepository;
    private final ICatalogFactory catalogFactory;
    private Logger logger = LogManager.getLogger();
    private HashMap<Integer, ICatalogItem> catalogItems;
    private HashMap<Integer, ICatalogPage> catalogPages;


    private static final String DEFAULT_PURCHASE_HANDLER = "default_purchase_handler";
    private final HashMap<String, ICatalogPurchaseHandler> purchaseHandlers;

    @Inject
    public CatalogManager(
            IFurnitureManager furnitureManager
            , ICatalogRepository catalogRepository
            , ICatalogFactory catalogFactory,
            Injector injector) {
        this.furnitureManager = furnitureManager;
        this.catalogRepository = catalogRepository;
        this.catalogFactory = catalogFactory;

        this.catalogItems = new HashMap<>();
        this.catalogPages = new HashMap<>();
        this.purchaseHandlers = new HashMap<>();

        this.purchaseHandlers.put(DEFAULT_PURCHASE_HANDLER, new DefaultCatalogPurchaseHandler());
        this.purchaseHandlers.put(TeleportPurchaseHandler.HandlerId, new TeleportPurchaseHandler());
        for (var handler : this.purchaseHandlers.values())
            injector.injectMembers(handler);
    }

    @Override
    public void init() throws InterruptedException {
        this.logger.info("Initializing catalog from database...");
        this.catalogRepository.getAllCatalogItems(result -> {
            if (result == null) return;
            try {

                var id = result.getInt("id");
                if (id == -1) throw new IllegalArgumentException("Catalog bundles is not supported.");
                var itemsId = result.getString("item_ids");
                if (!StringUtils.isStrictlyNumeric(itemsId))
                    throw new IllegalArgumentException("Catalog multi-item is not supported.");

                var catalogItem = catalogFactory.createCatalogItem(result);
                catalogItems.put(catalogItem.getId(), catalogItem);
            } catch (Exception e) {
                logger.error("Error while trying to fetch catalog item {}", result.getInt("id"), e);
            }
        });
        this.logger.info(STR."Loaded \{this.catalogItems.size()} catalog items from database.");


        this.catalogRepository.getAllCatalogPages(result -> {
            if (result == null) return;
            
            try {
                var pageId = result.getInt("id");
                var items = new ArrayList<ICatalogItem>();
                for (var item : catalogItems.values()) {
                    if (item.getPageId() == pageId)
                        items.add(item);
                }

                var catalogPage = catalogFactory.createCatalogPage(result, items);
                catalogPages.put(catalogPage.getId(), catalogPage);
            } catch (Exception e) {
                logger.error("Error while trying to fetch catalog page{}", result.getInt("id"), e);
            }
        });
        this.logger.info(STR."Loaded \{this.catalogPages.size()} catalog pages from database.");

        for (var catalogPage : catalogPages.values()) {
            var parent = catalogPages.get(catalogPage.getParentId());
            if (parent != null) {
                parent.getChildren().add(catalogPage);
            }
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public HashMap<Integer, ICatalogItem> getCatalogItems() {
        return catalogItems;
    }

    public void setCatalogItems(HashMap<Integer, ICatalogItem> catalogItems) {
        this.catalogItems = catalogItems;
    }

    @Override
    public HashMap<Integer, ICatalogPage> getCatalogPages() {
        return this.catalogPages;
    }

    @Override
    public List<ICatalogPage> getCatalogPagesForHabbo(int parentId, IHabbo habbo) {
        var pages = new ArrayList<ICatalogPage>(this.catalogPages.size());
        for (var page : this.catalogPages.values()) {
            if (page.getParentId() != parentId) continue;
            if (!page.isEnabled() || page.getMinRank() > habbo.getData().getRank())
                continue;

            pages.add(page);
        }

        return pages;
    }

    @Override
    public @Nullable ICatalogPage getCatalogPageForHabbo(int pageId, IHabbo habbo) {
        var page = this.catalogPages.get(pageId);
        if (page == null) return null;
        if (!page.isEnabled() || page.getMinRank() > habbo.getData().getRank())
            return null;

        return page;
    }

    @Override
    public ICatalogPurchaseHandler getPurchaseHandlerForItem(ICatalogItem item) {
        if (this.purchaseHandlers.containsKey(item.getFurniture().getInteractionType()))
            return this.purchaseHandlers.get(item.getFurniture().getInteractionType());

        return this.purchaseHandlers.get(DEFAULT_PURCHASE_HANDLER);
    }
}
