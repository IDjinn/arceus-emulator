package habbo.furniture;

import core.IHotelService;
import habbo.furniture.extra.data.IExtraData;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public interface IFurnitureManager extends IHotelService {
    HashMap<Integer, IFurniture> getAll();

    @Nullable IFurniture get(int id);

    IExtraData parseExtraData(String json);
}
