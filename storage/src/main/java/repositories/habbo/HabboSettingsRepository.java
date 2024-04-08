package repositories.habbo;

import com.google.inject.Singleton;
import queries.habbo.HabboSettingsQuery;
import repositories.ConnectionRepository;
import storage.repositories.habbo.IHabboSettingsRepository;

@Singleton
public class HabboSettingsRepository extends ConnectionRepository implements IHabboSettingsRepository {
    public void createDefaultNavigatorWindowSettings(Object... parameters) {
        this.insert(HabboSettingsQuery.INSERT_DEFAULT_NAVIGATOR_WINDOW_SETTINGS.get(), null, parameters);
    }
}
