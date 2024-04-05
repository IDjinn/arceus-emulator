package habbo.catalog.items;

import habbo.furniture.IFurniture;
import networking.packets.OutgoingPacket;
import networking.util.ISerializable;
import utils.IFillable;

public interface ICatalogItem extends IFillable, ISerializable {
    void compose(OutgoingPacket packet);

    void composeClubPresents(OutgoingPacket packet);

    void serializeAvailability(OutgoingPacket packet);

    int getId();

    IFurniture getFurniture();

    String getDisplayName();

    int getCostCredits();

    int getCostActivityPoints();

    int getCostDiamonds();

    int getCostSeasonal();

    int getAmount();

    boolean isVip();

    int getLimitedTotal();

    int getLimitedSells();

    boolean allowOffer();

    void increaseLimitedSells(int amount);

    boolean hasBadge();

    boolean isBadgeOnly();

    String getBadgeId();

    String getPresetData();

    int getPageId();

    int getOrder();

    int compareTo(ICatalogItem item);
}