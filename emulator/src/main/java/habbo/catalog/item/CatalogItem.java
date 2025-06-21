package habbo.catalog.item;

import com.google.inject.Inject;
import habbo.catalog.items.ICatalogItem;
import habbo.furniture.IFurniture;
import habbo.furniture.IFurnitureManager;
import io.netty.util.internal.StringUtil;
import lombok.Getter;
import networking.packets.OutgoingPacket;
import storage.results.IConnectionResult;

@Getter
public class CatalogItem implements ICatalogItem {
    private final IFurnitureManager furnitureManager;

    @Inject
    public CatalogItem(IFurnitureManager furnitureManager) {
        this.furnitureManager = furnitureManager;
    }

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
    private boolean isAllowOffer;
    private String badgeId;
    private String presetData;
    private int pageId;
    private int order;


    @Override
    public boolean hasBadge() {
        return !StringUtil.isNullOrEmpty(this.badgeId);
    }

    @Override
    public boolean isBadgeOnly() {
        return this.hasBadge() && this.items.isEmpty();
    }

    public int compareTo(ICatalogItem item) {
        return this.order - item.getOrder();
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
        this.isAllowOffer = result.getBoolean("have_offer");
        this.badgeId = "";//result.getString("badge_id");
        this.presetData = result.getString("extradata");
        this.pageId = result.getInt("page_id");
        this.order = result.getInt("order_number");
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
                    .appendString(this.getPresetData(), "extraData")
                    .appendInt(this.getAmount(), "itemAmount")
                    .appendBoolean(this.getLimitedTotal() > 0, "isLimited");
            if (this.getLimitedTotal() > 0) {
                packet.appendInt(this.getLimitedTotal());
                packet.appendInt(this.getLimitedTotal() - this.getLimitedSells());
            }
        }

        packet
                .appendInt(0) // TODO clubOnly
                .appendBoolean(this.isAllowOffer(), "_bundlePurchaseAllowed") // TODO
                .appendBoolean(false, "_isPet")
                .appendString(this.getDisplayName() + ".png");
    }
}