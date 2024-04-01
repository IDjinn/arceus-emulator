package core.furniture;

import com.google.common.collect.ImmutableCollection;
import core.IHotelService;
import org.jetbrains.annotations.Nullable;

public interface IFurnitureManager extends IHotelService {
    public ImmutableCollection<IFurniture> getAll();
    
    public @Nullable IFurniture get(int id);
}
