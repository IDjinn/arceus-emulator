package furniture.factory;

import furniture.Furniture;
import habbo.furniture.IFurniture;
import habbo.furniture.IFurnitureFactory;
import storage.results.IConnectionResult;

public class FurnitureFactory implements IFurnitureFactory {
    @Override
    public IFurniture create(IConnectionResult result) throws Exception {
        return new Furniture(result);
    }
}
