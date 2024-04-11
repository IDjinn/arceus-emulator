package habbo.catalog.item;

import com.google.inject.Inject;
import habbo.catalog.items.ICatalogItem;
import habbo.furniture.IFurniture;
import habbo.furniture.IFurnitureManager;
import io.netty.util.internal.StringUtil;
import networking.packets.OutgoingPacket;
import storage.results.IConnectionResult;

public class CatalogItem implements ICatalogItem {

    private final IFurnitureManager furnitureManager;


    private int id;

    private IFurniture furniture;


    private String displayName;


    private int costCredits;


    private int costActivityPoints;


    private int costDiamonds;


    private int costSeasonal;


    private int amount;


    private boolean vip;

    private String items;
    private int limitedTotal;

    private int limitedSells;

    private boolean allowOffer;


    private String badgeId;

    private String presetData;

    private int pageId;

    private int orderNum;

    @Inject
    public CatalogItem(IFurnitureManager furnitureManager) {
        this.furnitureManager = furnitureManager;
    }


    @Override
    public void compose(OutgoingPacket packet) {

    }

    @Override
    public void composeClubPresents(OutgoingPacket packet) {

    }

    @Override
    public void serializeAvailability(OutgoingPacket packet) {

    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public IFurniture getFurniture() {
        return this.furniture;
    }


    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public int getCostCredits() {
        return this.costCredits;
    }

    @Override
    public int getCostActivityPoints() {
        return this.costActivityPoints;
    }

    @Override
    public int getCostDiamonds() {
        return this.costDiamonds;
    }

    @Override
    public int getCostSeasonal() {
        return this.costSeasonal;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public boolean isVip() {
        return this.vip;
    }

    @Override
    public int getLimitedTotal() {
        return this.limitedTotal;
    }

    @Override
    public int getLimitedSells() {
        return this.limitedSells;
    }

    @Override
    public boolean allowOffer() {
        return this.allowOffer;
    }

    @Override
    public void increaseLimitedSells(int amount) {

    }


    @Override
    public boolean hasBadge() {
        return !StringUtil.isNullOrEmpty(this.badgeId);
    }

    @Override
    public boolean isBadgeOnly() {
        return hasBadge() && this.items.isEmpty();
    }

    @Override
    public String getBadgeId() {
        return this.badgeId;
    }

    @Override
    public String getPresetData() {
        return this.presetData;
    }

    @Override
    public int getPageId() {
        return this.pageId;
    }


    @Override
    public int getOrder() {
        return this.orderNum;
    }

    public int compareTo(ICatalogItem item) {
        return this.orderNum - item.getOrder();
    }

    @Override
    public void fill(IConnectionResult result) throws Exception {
        this.id = result.getInt("id");
        this.furniture = this.furnitureManager.get(Integer.parseInt(result.getString("item_ids")));
        this.displayName = result.getString("catalog_name");
        this.costCredits = result.getInt("cost_credits");
        this.costActivityPoints = result.getInt("cost_points");
        this.costDiamonds = 0;
        this.costSeasonal = 0;
        this.amount = result.getInt("amount");
        this.vip = result.getBoolean("club_only");
//        this.items = result.getString("items");
        this.items = "";
        this.limitedTotal = result.getInt("limited_stack");
        this.limitedSells = result.getInt("limited_sells");
        this.allowOffer = result.getBoolean("have_offer");
        this.badgeId = "";//result.getString("badge_id");
        this.presetData = result.getString("extradata");
        this.pageId = result.getInt("page_id");
        this.orderNum = result.getInt("order_number");
    }

    @Override
    public void serialize(OutgoingPacket packet) {
        packet.appendInt(this.getId())
                .appendString(this.getDisplayName())
                .appendBoolean(false, "rent") //rent
                .appendInt(this.getCostCredits())
                .appendInt(0, "_priceActivityPoints") // TODO OTHER CURRENCY TYPE
                .appendInt(0, "_priceActivityPointsType")
                .appendBoolean(false, "_giftable");// TODO GIFT

        packet.appendInt(1, "bundle items?");
        {
            packet.appendString(this.getFurniture().getType().toString())
                    .appendInt(this.getFurniture().getSpriteId())
                    .appendString(getPresetData(), "extraData")
                    .appendInt(this.getAmount(), "itemAmount")
                    .appendBoolean(getLimitedTotal() > 0, "isLimited");
            if (getLimitedTotal() > 0) {
                packet.appendInt(this.getLimitedTotal());
                packet.appendInt(this.getLimitedTotal() - this.getLimitedSells());
            }
        }

        packet.appendInt(0)// TODO clubOnly
                .appendBoolean(this.allowOffer(), "_bundlePurchaseAllowed") // TODO
                .appendBoolean(false, "_isPet")
                .appendString(STR."\{this.getDisplayName()}.png");
    }
}