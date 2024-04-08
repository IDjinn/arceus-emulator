package habbo.catalog;

import com.google.inject.Inject;
import habbo.catalog.items.ICatalogItem;
import habbo.furniture.extra.data.LegacyExtraData;
import habbo.habbos.IHabbo;
import habbo.habbos.factories.IHabboInventoryItemFactory;
import habbo.rooms.components.objects.items.LimitedData;
import packets.outgoing.catalog.PurchaseOkComposer;
import packets.outgoing.inventory.AddHabboItemCategory;
import packets.outgoing.inventory.AddHabboItemComposer;
import packets.outgoing.inventory.InventoryRefreshComposer;
import storage.repositories.habbo.IHabboInventoryRepository;

import java.util.ArrayList;


public class DefaultCatalogPurchaseHandler implements ICatalogPurchaseHandler {
    @Inject
    private IHabboInventoryRepository inventoryRepository;
    @Inject
    private IHabboInventoryItemFactory inventoryItemFactory;

    @Override
    public boolean canPurchase(IHabbo habbo, ICatalogItem item, String extraData, int amount) {
        return amount > 0 && amount <= 100 && habbo.getInventory().canPurchaseItems(item.getAmount() * amount);
    }

    @Override
    public boolean purchase(IHabbo habbo, ICatalogItem item, String extraData, int amount) {
        var unseen = new ArrayList<Integer>();
        inventoryRepository.createInventoryItem(statement -> {
//            INSERT INTO items (user_id, item_id, extra_data, limited_data) VALUES (?, ?, ?, ?);
            for (var i = 0; i < amount; i++) {
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

            var inventoryItem = inventoryItemFactory.create(habbo, itemId, item.getFurniture(), itemExtraData);
            habbo.getInventory().addItem(inventoryItem);
            unseen.add(itemId);
        });

        habbo.getClient().sendMessages(
                new AddHabboItemComposer(AddHabboItemCategory.OwnedFurni, unseen),
                new PurchaseOkComposer(item),
                new InventoryRefreshComposer()
        );
        return true;
    }
}