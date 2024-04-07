package habbo.navigator.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NavigatorEventCategory implements INavigatorEventCategory {
    private final Logger logger = LogManager.getLogger();

    private int id;
    private String name;
    private boolean isVisible;

    public NavigatorEventCategory(String data) {
        try {
            this.fill(data);
        } catch (Exception e) {
            logger.error("Failed to fill navigator event category", e);
        }
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isVisible() {
        return this.isVisible;
    }

    private void fill(String result) throws Exception {
        final String[] data = result.split(",");

        this.id = Integer.parseInt(data[0]);
        this.name = data[1];
        this.isVisible = data[2].equalsIgnoreCase("true");
    }
}
