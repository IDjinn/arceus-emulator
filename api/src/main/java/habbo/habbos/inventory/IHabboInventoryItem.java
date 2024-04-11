package habbo.habbos.inventory;

import habbo.furniture.IFurniture;
import habbo.furniture.extra.data.IExtraData;
import habbo.habbos.IHabbo;
import networking.util.ISerializable;
import org.jetbrains.annotations.Nullable;
import utils.interfaces.IFillable;

public interface IHabboInventoryItem extends IFillable, ISerializable {
    int getId();

    IHabbo getHabbo();

    IFurniture getFurniture();

    IExtraData getExtraData();


    @Nullable String getWiredData(); // TODO MOVE TO OWN TABLE

    int getGroup();

}
