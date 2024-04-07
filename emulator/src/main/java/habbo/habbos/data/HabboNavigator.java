package habbo.habbos.data;

import com.google.inject.Inject;
import habbo.habbos.IHabbo;
import habbo.habbos.data.navigator.HabboNavigatorSearch;
import habbo.habbos.data.navigator.HabboNavigatorWindowSettings;
import habbo.habbos.data.navigator.IHabboNavigatorSearch;
import habbo.habbos.data.navigator.IHabboNavigatorWindowSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.repositories.habbo.IHabboNavigatorRepository;
import storage.results.IConnectionResult;

import java.util.ArrayList;
import java.util.List;

public class HabboNavigator implements IHabboNavigator {
    private final Logger logger = LogManager.getLogger();

    @Inject
    private IHabboNavigatorRepository repository;

    private final IHabbo habbo;

    private final IHabboNavigatorWindowSettings navigatorWindowSettings;

    private final List<IHabboNavigatorSearch> navigatorSearches;

    public HabboNavigator(IHabbo habbo, IConnectionResult data) {
        this.habbo = habbo;

        this.navigatorSearches = new ArrayList<>();
        this.navigatorWindowSettings = new HabboNavigatorWindowSettings(data);
    }

    public IHabboNavigatorWindowSettings getNavigatorWindowSettings() {
        return navigatorWindowSettings;
    }

    public List<IHabboNavigatorSearch> getNavigatorSearches() {
        return navigatorSearches;
    }

    public void init() {
        try {
            this.loadNavigatorSearches();
        } catch (Exception e) {
            logger.error("Failed to fill [HabboNavigator]", e);
        }
    }

    private void loadNavigatorSearches() {
        this.repository.loadNavigatorSearches(result -> {
            if(result == null) return;

            final IHabboNavigatorSearch search = new HabboNavigatorSearch(result);

            this.navigatorSearches.add(search);
        }, this.habbo.getData().getId());
    }
}
