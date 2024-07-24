package habbo.habbos.inventory;

import habbo.habbos.IHabboComponent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public interface IHabboInventory extends IHabboComponent {
    HashMap<Integer, IHabboInventoryItem> getItems();

    void addItem(IHabboInventoryItem item);

    void removeItem(long id);

    @Nullable IHabboInventoryItem getItem(int id);

    boolean canPurchaseItems(int count);

    void sendUpdate();
}
