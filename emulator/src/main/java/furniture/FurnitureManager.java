package furniture;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.furniture.IFurniture;
import habbo.furniture.IFurnitureFactory;
import habbo.furniture.IFurnitureManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import storage.repositories.furniture.IFurnitureRepository;

import java.util.HashMap;

@Singleton
public class FurnitureManager implements IFurnitureManager {
    private final IFurnitureRepository furnitureRepository;
    private final HashMap<Integer, IFurniture> furnitures;
    private final IFurnitureFactory furnitureFactory;
    private Logger logger = LogManager.getLogger();


    @Inject
    public FurnitureManager(IFurnitureRepository furnitureRepository, IFurnitureFactory furnitureFactory) {
        this.furnitureRepository = furnitureRepository;
        this.furnitureFactory = furnitureFactory;
        this.furnitures = new HashMap<>(10_000);
    }

    @Override
    public void init() throws InterruptedException {
        this.logger.info("Initializing furnitures from database...");
        this.furnitureRepository.getAllFurniture(result -> {
            if (result == null) return;

            var furniture = furnitureFactory.create(result);
            furnitures.put(furniture.getId(), furniture);
        });

        this.logger.info(STR."Loaded \{this.furnitures.size()} furniture from database.");
    }

    @Override
    public void destroy() {
        this.furnitures.values().forEach(IFurniture::destroy);
        this.furnitures.clear();
    }

    @Override
    public HashMap<Integer,IFurniture> getAll() {
        return this.furnitures;
    }

    @Override
    public @Nullable IFurniture get(int id) {
        return this.furnitures.get(id);
    }
}
