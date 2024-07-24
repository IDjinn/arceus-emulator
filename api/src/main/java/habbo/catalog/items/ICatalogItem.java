package habbo.catalog.items;

import habbo.furniture.IFurniture;
import utils.interfaces.IFillable;

public interface ICatalogItem extends IFillable {

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

    boolean hasBadge();

    boolean isBadgeOnly();

    String getBadgeId();

    String getPresetData();

    int getPageId();

    int getOrder();

    int compareTo(ICatalogItem item);
}