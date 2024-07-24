package habbo.navigator;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import core.configuration.IEmulatorSettings;
import habbo.navigator.data.*;
import habbo.navigator.tabs.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import storage.repositories.navigator.INavigatorRepository;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class NavigatorManager implements INavigatorManager {
    private final Logger logger = LogManager.getLogger();

    private final Injector injector;

    private final HashMap<Integer, INavigatorPublicCategory> publicCategories;

    private final INavigatorRepository repository;

    private final IEmulatorSettings emulatorSettings;

    private final HashMap<String, INavigatorFilterType> filterTypes;

    private final HashMap<Integer, INavigatorEventCategory> eventCategories;

    private final HashMap<String, INavigatorTab> tabs;

    @Inject
    public NavigatorManager(Injector injector, INavigatorRepository repository, IEmulatorSettings emulatorSettings) {
        this.injector = injector;
        this.repository = repository;
        this.emulatorSettings = emulatorSettings;

        this.tabs = new HashMap<>();
        this.filterTypes = new HashMap<>();
        this.publicCategories = new HashMap<>();
        this.eventCategories = new HashMap<>();
    }

    public void init() {
        this.loadPublicCategories();
        this.loadFilterTypes();
        this.loadEventCategories();
        this.registerTabs();
    }

    private void loadPublicCategories() {
        this.repository.loadPublicCategories(result -> {
            if(result == null) return;

            final INavigatorPublicCategory category = new NavigatorPublicCategory(result);

            this.publicCategories.put(category.getId(), category);
        });

        this.logger.info("Loaded {} navigator public categories", this.publicCategories.size());
    }

    private void loadFilterTypes() {
        this.repository.loadFilterTypes(result -> {
            if (result == null) return;

            final NavigatorFilterType filterType = new NavigatorFilterType(result);

            this.filterTypes.put(filterType.getKey(), filterType);
        });

        this.logger.info("Loaded {} navigator filter types", this.filterTypes.size());
    }

    private void loadEventCategories() {
        final String eventCategories = this.emulatorSettings.getOrDefault("navigator.eventcategories", "").trim();

        if(eventCategories.isBlank()) return;

        for(String eventCategory : eventCategories.split(";")) {
            final INavigatorEventCategory category = new NavigatorEventCategory(eventCategory);

            this.eventCategories.put(category.getId(), category);
        }

        this.logger.info("Loaded {} navigator event categories", this.eventCategories.size());
    }

    private void registerTabs() {
        this.tabs.putIfAbsent(NavigatorOfficialTab.FILTER_NAME, new NavigatorOfficialTab());
        this.tabs.putIfAbsent(NavigatorRecommendedTab.FILTER_NAME, new NavigatorRecommendedTab());
        this.tabs.putIfAbsent(NavigatorEventsTab.FILTER_NAME, new NavigatorEventsTab());
        this.tabs.putIfAbsent(NavigatorHabboTab.FILTER_NAME, new NavigatorHabboTab());

        this.tabs.forEach((_, tab) -> this.injector.injectMembers(tab));
    }

    @Override
    public Map<Integer, INavigatorEventCategory> getEventCategories() {
        return this.eventCategories;
    }

    @Override
    public Map<Integer, INavigatorPublicCategory> getPublicCategories() {
        return this.publicCategories;
    }

    @Override
    public INavigatorPublicCategory getPublicCategoryById(int id) {
        return this.publicCategories.get(id);
    }

    @Override
    public INavigatorFilterType getFilterTypeByKey(String key) {
        return this.filterTypes.get(key);
    }

    @Override
    public INavigatorFilterType getReplaceableFilterTypeByKey(String key, String fallbackKey) {
        if(key == null || key.isBlank()) {
            key = fallbackKey;
        }

        if(this.filterTypes.containsKey(key)) {
            return this.filterTypes.get(key);
        }

        return this.filterTypes.get(fallbackKey);
    }

    @Override
    public String normalizeTab(@Nullable String view) {
        if (view == null || view.equals("query") || view.equals("groups")) {
            return "hotel_view";
        }

        return view.trim();
    }

    public INavigatorTab getTab(String tabName) {
        return this.tabs.get(tabName);
    }

    @Override
    public void destroy() {

    }
}
