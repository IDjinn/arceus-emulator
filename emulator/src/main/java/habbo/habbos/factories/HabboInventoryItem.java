package habbo.habbos.factories;

import com.google.inject.Inject;
import habbo.furniture.FurnitureType;
import habbo.furniture.IFurniture;
import habbo.furniture.IFurnitureManager;
import habbo.habbos.inventory.IHabboInventoryItem;
import habbo.rooms.components.objects.items.ILimitedData;
import habbo.rooms.components.objects.items.LimitedData;
import org.jetbrains.annotations.Nullable;
import storage.results.IConnectionResult;

public class HabboInventoryItem implements IHabboInventoryItem {
    private int id;
    private FurnitureType type;
    private IFurniture furniture;
    private @Nullable String extraData;
    private ILimitedData limitedData;
    private @Nullable String wiredData;
    private int group;

    @Inject
    private IFurnitureManager furnitureManager;

    @Override
    public int getId() {
        return this.id;
    }


    @Override
    public IFurniture getFurniture() {
        return this.furniture;
    }

    @Override
    public @Nullable String getExtraData() {
        return this.extraData;
    }

    @Override
    public void setExtraData(@Nullable String extraData) {
        this.extraData = extraData;
    }

    @Override
    public ILimitedData getLimitedData() {
        return this.limitedData;
    }

    @Override
    public @Nullable String getWiredData() {
        return this.wiredData;
    }

    @Override
    public int getGroup() {
        return this.group;
    }

    @Override
    public void fill(IConnectionResult result) throws Exception {
        this.id = result.getInt("id");
        this.furniture = furnitureManager.get(result.getInt("item_id"));
        if (this.furniture == null)
            throw new IllegalArgumentException(STR."Invalid furniture base id for item id \{this.id}");

        this.extraData = result.getString("extra_data");
        this.limitedData = LimitedData.fromString(result.getString("limited_data"));
        this.wiredData = result.getString("wired_data");
        this.group = result.getInt("group");
    }
}
