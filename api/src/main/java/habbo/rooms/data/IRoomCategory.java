package habbo.rooms.data;

import habbo.navigator.enums.NavigatorDisplayMode;
import org.apache.logging.log4j.Logger;

public interface IRoomCategory {
    int getId();

    int getMinRank();

    String getCaption();

    String getCaptionSave();

    boolean isCanTrade();

    int getMaxUserCount();

    boolean isPublic();

    Logger getLogger();

    NavigatorDisplayMode getDisplayMode();

    int getOrder();
}
