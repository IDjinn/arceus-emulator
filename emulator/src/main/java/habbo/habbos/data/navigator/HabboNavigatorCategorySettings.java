package habbo.habbos.data.navigator;

import habbo.navigator.enums.NavigatorDisplayMode;
import habbo.navigator.enums.NavigatorLayoutDisplay;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.results.IConnectionResult;
import utils.interfaces.IFillable;

@Getter
public class HabboNavigatorCategorySettings implements IHabboNavigatorCategorySettings, IFillable {
    private final Logger logger = LogManager.getLogger();

    private String caption;
    private NavigatorDisplayMode displayMode;
    private NavigatorLayoutDisplay layoutDisplay;

    public HabboNavigatorCategorySettings(IConnectionResult data) {
        try {
            this.fill(data);
        } catch (Exception e) {
            this.logger.error("Failed to fill [HabboNavigatorCategorySettings]", e);
        }
    }

    @Override
    public void fill(IConnectionResult result) throws Exception {
        this.caption = result.getString("caption");
        this.displayMode = NavigatorDisplayMode.fromString(result.getString("list_type"));
        this.layoutDisplay = NavigatorLayoutDisplay.fromString(result.getString("display"));
    }
}
