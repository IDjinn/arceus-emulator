package furniture;

import com.google.common.collect.ImmutableCollection;
import com.google.inject.Inject;
import core.furniture.IFurniture;
import core.furniture.IFurnitureManager;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class FurnitureManager implements IFurnitureManager {
    private final HashMap<Integer, IFurniture> furnitures;
    

    public FurnitureManager() {
        this.furnitures = new HashMap<>(10_000);
    }

    @Override
    public void init() throws InterruptedException {
        
    }

    @Override
    public void destroy() {

    }

    @Override
    public HashMap<Integer,IFurniture> getAll() {
        return this.furnitures;
    }

    @Override
    public @Nullable IFurniture get(int id) {
        return null;
    }
}
