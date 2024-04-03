package storage.repositories.furniture;

import core.furniture.IFurniture;

import java.util.List;
import java.util.SequencedCollection;

public interface IFurnitureRepository {
    public SequencedCollection<IFurniture> getAllFurniture();
}
