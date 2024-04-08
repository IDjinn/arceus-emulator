package habbo.habbos.data.navigator;

import habbo.habbos.writers.HabboNavigatorSearchWriter;
import networking.packets.OutgoingPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.results.IConnectionResult;
import utils.IFillable;
import utils.IWriteable;

public class HabboNavigatorSearch implements IHabboNavigatorSearch {
    private final Logger logger = LogManager.getLogger();

    private String searchCode;
    private String filter;
    private int id;

    public HabboNavigatorSearch(IConnectionResult result) {
        try {
            this.fill(result);
        } catch (Exception e) {
            logger.error("Failed to fill navigator search", e);
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

    public void write(OutgoingPacket packet) {
        HabboNavigatorSearchWriter.write(this, packet);
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
