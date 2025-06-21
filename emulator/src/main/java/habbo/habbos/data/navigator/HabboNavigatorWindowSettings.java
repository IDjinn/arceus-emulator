package habbo.habbos.data.navigator;

import com.google.inject.Inject;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.results.IConnectionResult;
import utils.interfaces.IFillable;

public class HabboNavigatorWindowSettings implements IHabboNavigatorWindowSettings, IFillable {
    @Getter
    private final Logger logger = LogManager.getLogger();

    private int userId = -1;
    @Getter
    private int windowX = 100;
    @Getter
    private int windowY = 100;
    @Getter
    private int windowWidth = 425;
    @Getter
    private int windowHeight = 535;
    @Getter
    private boolean leftPanelCollapsed = false;
    @Getter
    public final int resultsMode = 0;

    @Inject
    public HabboNavigatorWindowSettings(IConnectionResult result) {
        try {
            this.fillOrCreate(result);
        } catch (Exception e) {
            this.logger.error(
                    "Failed to create HabboNavigatorWindowSettings from IConnectionResult: {}",
                    e.getMessage(),
                    e
            );
        }
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
