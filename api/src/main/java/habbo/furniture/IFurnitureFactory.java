package habbo.furniture;

import storage.results.IConnectionResult;

public interface IFurnitureFactory {
    IFurniture create(IConnectionResult result) throws Exception;
}
