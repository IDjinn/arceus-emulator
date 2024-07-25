package habbo.catalog.handlers;

import com.google.inject.Inject;
import habbo.catalog.ICatalogPurchaseHandler;
import habbo.catalog.items.ICatalogItem;
import habbo.furniture.extra.data.LegacyExtraData;
import habbo.habbos.IHabbo;
import habbo.habbos.factories.IHabboInventoryItemFactory;
import habbo.rooms.components.objects.items.LimitedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import packets.outgoing.catalog.PurchaseOkComposer;
import packets.outgoing.inventory.AddHabboItemCategory;
import packets.outgoing.inventory.AddHabboItemComposer;
import packets.outgoing.inventory.InventoryRefreshComposer;
import storage.repositories.furniture.IFurnitureRepository;
import storage.repositories.habbo.IHabboInventoryRepository;

import java.util.ArrayList;


public class TeleportPurchaseHandler implements ICatalogPurchaseHandler {
    public final static String HandlerId = "teleport";
    private final Logger logger = LogManager.getLogger();
    @Inject
    private IHabboInventoryRepository inventoryRepository;
    @Inject
    private IHabboInventoryItemFactory inventoryItemFactory;
    @Inject
    private IFurnitureRepository furnitureRepository;

    @Override
    public boolean canPurchase(IHabbo habbo, ICatalogItem item, String extraData, int amount) {
        return amount == 1 && habbo.getInventory().canPurchaseItems(2);
    }

    @Override
    public boolean purchase(IHabbo habbo, ICatalogItem item, String extraData, int amount) {
        var unseen = new ArrayList<Integer>();
        this.inventoryRepository.createInventoryItem(statement -> {
            for (var i = 0; i < amount * 2; i++) {
                statement.setInt(1, habbo.getData().getId());
                statement.setInt(2, item.getFurniture().getId());
                statement.setString(3, extraData);
                statement.setString(4, "0:0"); // TODO: LTD
                statement.addBatch();
            }
        }, result -> {
            var itemId = result.getInt(1);
            var itemExtraData = new LegacyExtraData(extraData);
            itemExtraData.setLimitedData(LimitedData.fromString("0:0"));

            var inventoryItem = this.inventoryItemFactory.create(habbo, itemId, item.getFurniture(), itemExtraData);
            habbo.getInventory().addItem(inventoryItem);
            unseen.add(itemId);
        });

        for (var i = 0; i < amount * 2; i += 2) {
            var first = unseen.get(i);
            var second = unseen.get(i + 1);

            this.furnitureRepository.createTeleportLink(result -> {
                if (result == null)
                    this.logger.error("error while creating teleport link for player {} with items ({}, {})", habbo.getData().getId(),
                            first, second);
            }, first, second);
        }

        habbo.getClient().sendMessages(
                new AddHabboItemComposer(AddHabboItemCategory.OwnedFurni, unseen),
                new PurchaseOkComposer(item),
                new InventoryRefreshComposer()
        );
        return true;
    }
}