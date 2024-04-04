package habbo.habbos.factories;

import habbo.habbos.inventory.IHabboInventoryItem;
import storage.results.IConnectionResult;

public interface IHabboInventoryItemFactory {
    public IHabboInventoryItem create(IConnectionResult data) throws Exception;
}
