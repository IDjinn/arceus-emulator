package habbo.habbos.data.navigator;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.results.IConnectionResult;
import utils.interfaces.IFillable;

public class HabboNavigatorWindowSettings implements IHabboNavigatorWindowSettings, IFillable {
    private final Logger logger = LogManager.getLogger();

    private int userId = -1;
    private int windowX = 100;
    private int windowY = 100;
    private int windowWidth = 425;
    private int windowHeight = 535;
    private boolean leftPanelCollapsed = false;
    public int resultsMode = 0;

    @Inject
    public HabboNavigatorWindowSettings(IConnectionResult result) {
        try {
            this.fillOrCreate(result);
        } catch (Exception e) {
            this.logger.error(STR."Failed to create HabboNavigatorWindowSettings from IConnectionResult: \{e.getMessage()}");
        }
    }

    public Logger getLogger() {
        return this.logger;
    }

    public int getWindowX() {
        return this.windowX;
    }

    public int getWindowY() {
        return this.windowY;
    }

    public int getWindowWidth() {
        return this.windowWidth;
    }

    public int getWindowHeight() {
        return this.windowHeight;
    }

    public boolean isLeftPanelCollapsed() {
        return this.leftPanelCollapsed;
    }

    public int getResultsMode() {
        return this.resultsMode;
    }

    private void fillOrCreate(IConnectionResult result) throws Exception {
        this.userId = result.getInt("id");

        if(!result.hasColumn("x")) return;

        this.fill(result);
    }

    @Override
    public void fill(IConnectionResult result) throws Exception {
        this.windowX = result.getInt("x");
        this.windowY = result.getInt("y");
        this.windowWidth = result.getInt("width");
        this.windowHeight = result.getInt("height");
        this.leftPanelCollapsed = result.getBoolean("open_searches");
    }
}
