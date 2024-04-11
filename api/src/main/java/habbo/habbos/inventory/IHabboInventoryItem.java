package habbo.habbos.inventory;

import habbo.furniture.IFurniture;
import habbo.furniture.extra.data.IExtraData;
import habbo.habbos.IHabbo;
import networking.util.ISerializable;
import org.jetbrains.annotations.Nullable;
import utils.interfaces.IFillable;

public interface IHabboInventoryItem extends IFillable, ISerializable {
    public int getId();

    public IHabbo getHabbo();
    
    public IFurniture getFurniture();

    public IExtraData getExtraData();


    public @Nullable String getWiredData(); // TODO MOVE TO OWN TABLE

    public int getGroup();

}
