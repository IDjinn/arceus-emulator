package habbo.habbos.inventory;

import habbo.furniture.FurnitureType;
import habbo.furniture.IFurniture;
import habbo.rooms.components.objects.items.ILimitedData;
import org.jetbrains.annotations.Nullable;

public interface IHabboInventoryItem {
    public int getId();

    public FurnitureType getType();

    public IFurniture getFurniture();

    public @Nullable String getExtraData();

    public void setExtraData(@Nullable String extraData);

    public ILimitedData getLimitedData();

    public @Nullable String getWiredData(); // TODO MOVE TO OWN TABLE

    public int getGroup();
}
