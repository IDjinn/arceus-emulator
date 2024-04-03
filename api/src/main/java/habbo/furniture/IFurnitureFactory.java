package habbo.furniture;

import storage.results.IConnectionResult;

public interface IFurnitureFactory {
    public IFurniture create(IConnectionResult result) throws Exception;
}
