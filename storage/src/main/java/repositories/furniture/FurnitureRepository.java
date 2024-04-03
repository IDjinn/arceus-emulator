package repositories.furniture;

import core.furniture.IFurniture;
import queries.furniture.FunitureQuery;
import queries.habbo.HabboQuery;
import repositories.ConnectionRepository;
import storage.repositories.furniture.IFurnitureRepository;
import storage.results.IConnectionResult;

import java.util.SequencedCollection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class FurnitureRepository extends ConnectionRepository implements IFurnitureRepository {
    @Override
    public SequencedCollection<IFurniture> getAllFurniture() {
        final AtomicReference<IConnectionResult> result = new AtomicReference<>(null);

        this.select(FunitureQuery.SELECT_ALL_FURNITURE.get(), connectionResult -> {
            if(connectionResult == null) return;

            result.set(connectionResult);
        });

        return null;
    }
}
