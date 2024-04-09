package habbo.habbos.inventory;

import habbo.habbos.IHabboComponent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public interface IHabboInventory extends IHabboComponent {
    public HashMap<Integer, IHabboInventoryItem> getItems();

    public void addItem(IHabboInventoryItem item);

    public void removeItem(long id);

    public @Nullable IHabboInventoryItem getItem(int id);

    public boolean canPurchaseItems(int count);
}
