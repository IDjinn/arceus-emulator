package habbo.furniture;

import core.IHotelService;
import habbo.furniture.extra.data.IExtraData;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public interface IFurnitureManager extends IHotelService {
    public HashMap<Integer,IFurniture> getAll();
    
    public @Nullable IFurniture get(int id);

    public IExtraData parseExtraData(String json);
}
