package habbo.habbos.data.navigator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.results.IConnectionResult;

public class HabboNavigatorSearch implements IHabboNavigatorSearch {
    private final Logger logger = LogManager.getLogger();

    private String searchCode;
    private String filter;
    private int id;

    public HabboNavigatorSearch(IConnectionResult result) {
        try {
            this.fill(result);
        } catch (Exception e) {
            this.logger.error("Failed to fill navigator search", e);
        }
    }

    @Override
    public String getSearchCode() {
        return this.searchCode;
    }

    @Override
    public String getFilter() {
        return this.filter;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public void write(IOutgoingDTOSerializer<U> packet) {
        packet.appendInt(search.getId());
        packet.appendString(search.getSearchCode());
        packet.appendString(search.getFilter());
        packet.appendString("");
    }

    @Override
    public void fill(IConnectionResult result) throws Exception {
        this.id = result.getInt("id");
        this.searchCode = result.getString("search_code");
        this.filter = !result.isNull("filter")
                ? result.getString("filter")
                : "";
    }
}
