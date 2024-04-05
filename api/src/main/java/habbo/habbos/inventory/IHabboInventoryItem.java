package habbo.habbos.inventory;

import habbo.furniture.IFurniture;
import habbo.rooms.components.objects.items.ILimitedData;
import networking.util.ISerializable;
import org.jetbrains.annotations.Nullable;
import utils.IFillable;

public interface IHabboInventoryItem extends IFillable, ISerializable {
    public int getId();

    public IFurniture getFurniture();

    public @Nullable String getExtraData();

    public void setExtraData(@Nullable String extraData);

    public ILimitedData getLimitedData();

    public @Nullable String getWiredData(); // TODO MOVE TO OWN TABLE

    public int getGroup();

    public boolean isLimited();
}
