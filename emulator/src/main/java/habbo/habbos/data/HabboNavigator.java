package habbo.habbos.data;

import com.google.inject.Inject;
import habbo.habbos.IHabbo;
import habbo.habbos.data.navigator.*;
import habbo.navigator.enums.NavigatorDisplayMode;
import habbo.navigator.enums.NavigatorLayoutDisplay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.repositories.habbo.IHabboNavigatorRepository;
import storage.results.IConnectionResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HabboNavigator implements IHabboNavigator {
    private final Logger logger = LogManager.getLogger();

    @Inject
    private IHabboNavigatorRepository repository;

    private final IHabbo habbo;

    private final IHabboNavigatorWindowSettings navigatorWindowSettings;

    private final List<IHabboNavigatorSearch> navigatorSearches;

    private final HashMap<String, IHabboNavigatorCategorySettings> navigatorCategoriesSettings;

    public HabboNavigator(IHabbo habbo, IConnectionResult data) {
        this.habbo = habbo;

        this.navigatorSearches = new ArrayList<>();
        this.navigatorWindowSettings = new HabboNavigatorWindowSettings(data);
        this.navigatorCategoriesSettings = new HashMap<>();
    }

    public void init() {
        try {
            this.loadNavigatorSearches();
            this.loadNavigatorCategoriesSettings();
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

    private void loadNavigatorCategoriesSettings() {
        this.repository.loadNavigatorCategoriesSettings(result -> {
            if(result == null) return;

            final IHabboNavigatorCategorySettings categorySettings = new HabboNavigatorCategorySettings(result);

            this.navigatorCategoriesSettings.put(categorySettings.getCaption(), categorySettings);
        }, this.habbo.getData().getId());
    }

    public IHabboNavigatorWindowSettings getNavigatorWindowSettings() {
        return navigatorWindowSettings;
    }

    public List<IHabboNavigatorSearch> getNavigatorSearches() {
        return navigatorSearches;
    }

    public IHabboNavigatorCategorySettings getNavigatorCategorySettingsFromName(String name) {
        return this.navigatorCategoriesSettings.get(name);
    }

    public NavigatorDisplayMode getDisplayModeForCategory(String name) {
        return this.getDisplayModeForCategory(name, NavigatorDisplayMode.LIST);
    }

    public NavigatorDisplayMode getDisplayModeForCategory(String name, NavigatorDisplayMode defaultMode) {
        final IHabboNavigatorCategorySettings categorySettings = this.getNavigatorCategorySettingsFromName(name);

        if(categorySettings != null) {
            return categorySettings.getDisplayMode();
        }

        return defaultMode;
    }

    public NavigatorLayoutDisplay getLayoutDisplayForCategory(String name) {
        return this.getLayoutDisplayForCategory(name, NavigatorLayoutDisplay.DEFAULT);
    }

    public NavigatorLayoutDisplay getLayoutDisplayForCategory(String name, NavigatorLayoutDisplay defaultLayout) {
        final IHabboNavigatorCategorySettings categorySettings = this.getNavigatorCategorySettingsFromName(name);

        if(categorySettings != null) {
            return categorySettings.getLayoutDisplay();
        }

        return defaultLayout;
    }
}
