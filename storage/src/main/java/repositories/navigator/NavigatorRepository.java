package repositories.navigator;

import com.google.inject.Singleton;
import queries.navigator.NavigatorQuery;
import repositories.ConnectionRepository;
import storage.repositories.navigator.INavigatorRepository;
import storage.results.IConnectionResultConsumer;

@Singleton
public class NavigatorRepository extends ConnectionRepository implements INavigatorRepository {
    public void loadPublicCategories(IConnectionResultConsumer consumer) {
        this.select(NavigatorQuery.SELECT_ALL_PUBLIC_CATEGORIES.get(), consumer);
    }

    public void loadFilterTypes(IConnectionResultConsumer consumer) {
        this.select(NavigatorQuery.SELECT_ALL_FILTER_TYPES.get(), consumer);
    }
}
