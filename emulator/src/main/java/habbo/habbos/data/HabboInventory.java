package habbo.habbos.data;

import com.google.inject.Inject;
import habbo.habbos.IHabbo;
import habbo.habbos.factories.IHabboInventoryItemFactory;
import habbo.habbos.inventory.IHabboInventory;
import habbo.habbos.inventory.IHabboInventoryItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import packets.outgoing.inventory.InventoryRefreshComposer;
import storage.repositories.habbo.IHabboInventoryRepository;

import java.util.HashMap;

public class HabboInventory implements IHabboInventory {
    private final HashMap<Integer, IHabboInventoryItem> items;
    private final IHabbo habbo;
    private final Logger logger = LogManager.getLogger();

    @Inject
    private IHabboInventoryRepository inventoryRepository;

    @Inject
    private IHabboInventoryItemFactory inventoryItemFactory;

    public HabboInventory(IHabbo habbo) {
        this.habbo = habbo;
        this.items = new HashMap<Integer, IHabboInventoryItem>();
    }

    @Override
    public HashMap<Integer, IHabboInventoryItem> getItems() {
        return this.items;
    }

    @Override
    public void addItem(IHabboInventoryItem item) {
        this.items.put(item.getId(), item);
    }

    @Override
    public void removeItem(long id) {
        this.items.remove(id);
    }

    @Override
    public @Nullable IHabboInventoryItem getItem(int id) {
        return this.items.get(id);
    }

    @Override
    public void init() {
        this.inventoryRepository.getInventoryByOwnerId(this.getHabbo().getData().getId(), result -> {
            if (result == null) return;
            try {
                var item = this.inventoryItemFactory.create(result, this.getHabbo());
                this.items.put(item.getId(), item);
            } catch (Exception e) {
                this.logger.error("Error while creating inventory item {} for habbo {}", result.getInt("id"), e);
            }
        });
    }

    @Override
    public void update() {

    }

    @Override
    public void destory() {

    }

    @Override
    public IHabbo getHabbo() {
        return this.habbo;
    }

    @Override
    public boolean canPurchaseItems(int count) {
        return count > 0;
    }

    @Override
    public void sendUpdate() {
        this.getHabbo().getClient().sendMessage(new InventoryRefreshComposer());
    }
}