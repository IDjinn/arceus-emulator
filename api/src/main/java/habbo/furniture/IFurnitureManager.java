package habbo.furniture;

import core.IHotelService;
import habbo.furniture.extra.data.IExtraData;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface IFurnitureManager extends IHotelService {
    Map<Integer, IFurniture> getAll();

    @Nullable IFurniture get(int id);

    IExtraData parseExtraData(String json);
}
