package repositories.habbo;

import com.google.inject.Singleton;
import queries.habbo.HabboNavigatorQuery;
import repositories.ConnectionRepository;
import storage.repositories.habbo.IHabboNavigatorRepository;
import storage.results.IConnectionResultConsumer;

@Singleton
public class HabboNavigatorRepository extends ConnectionRepository implements IHabboNavigatorRepository {
    public void loadNavigatorSearches(IConnectionResultConsumer consumer, int habboId) {
        this.select(HabboNavigatorQuery.SELECT_ALL_NAVIGATOR_SEARCHES.get(), consumer, habboId);
    }

    public void loadNavigatorCategoriesSettings(IConnectionResultConsumer consumer, int habboId) {
        this.select(HabboNavigatorQuery.SELECT_ALL_NAVIGATOR_CATEGORIES_SETTINGS.get(), consumer, habboId);
    }
}
