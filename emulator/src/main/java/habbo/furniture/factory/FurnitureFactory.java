package habbo.furniture.factory;

import com.google.inject.Inject;
import com.google.inject.Injector;
import habbo.furniture.Furniture;
import habbo.furniture.IFurniture;
import habbo.furniture.IFurnitureFactory;
import habbo.furniture.IFurnitureManager;
import storage.results.IConnectionResult;

public class FurnitureFactory implements IFurnitureFactory {
    @Inject
    private IFurnitureManager furnitureManager;
    @Inject
    private Injector injector;
    @Override
    public IFurniture create(IConnectionResult result) throws Exception {
        var furniture = new Furniture();
        injector.injectMembers(furniture);
        furniture.fill(result);
        return furniture;
    }
}
