package habbo.habbos.inventory;

import habbo.habbos.Habbo;
import org.jetbrains.annotations.Nullable;
import storage.results.IConnectionResult;

import java.util.HashMap;

public class HabboInventoryComponent implements IHabboInventoryComponent {
    private final Habbo habbo;

    public HabboInventoryComponent(Habbo habbo, IConnectionResult result) {
        this.habbo = habbo;
    }

    @Override
    public HashMap<Integer, IHabboInventoryItem> getItems() {
        return null;
    }

    @Override
    public void addItem(IHabboInventoryItem item) {

    }

    @Override
    public void removeItem(int id) {

    }

    @Override
    public @Nullable IHabboInventoryItem getItem(int id) {
        return null;
    }

    @Override
    public void init() {
        
    }

    @Override
    public void update() {

    }

    @Override
    public void destory() {

    }

    @Override
    public Habbo getHabbo() {
        return habbo;
    }
}
