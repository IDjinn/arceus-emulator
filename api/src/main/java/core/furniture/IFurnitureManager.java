package core.furniture;

import com.google.common.collect.ImmutableCollection;
import core.IHotelService;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public interface IFurnitureManager extends IHotelService {
    public HashMap<Integer,IFurniture> getAll();
    
    public @Nullable IFurniture get(int id);
}
