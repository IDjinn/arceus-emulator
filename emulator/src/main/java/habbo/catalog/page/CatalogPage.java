package habbo.catalog.page;

import com.google.common.collect.Lists;
import habbo.catalog.items.ICatalogItem;
import habbo.catalog.pages.CatalogPageType;
import habbo.catalog.pages.ICatalogPage;
import habbo.habbos.IHabbo;
import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.Nullable;
import storage.results.IConnectionResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CatalogPage implements ICatalogPage {
    private final List<ICatalogPage> children = Lists.newArrayList();
    private int id;
    private CatalogPageType type;
    private String caption;
    private int icon;
    private int minRank;
    private String template;
    private int parentId;
    private String linkName;
    private int order;
    private boolean enabled;
    private boolean vipOnly;
    private List<String> images;
    private List<String> texts;
    private HashMap<Integer, ICatalogItem> items = new HashMap<Integer, ICatalogItem>();
    private String extraData;

    @Override
    public int getOfferSize() {
        return 0;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getCaption() {
        return this.caption;
    }

    @Override
    public int getIcon() {
        return this.icon;
    }

    @Override
    public int getMinRank() {
        return this.minRank;
    }

    @Override
    public String getTemplate() {
        return this.template;
    }

    @Override
    public int getParentId() {
        return parentId;
    }

    @Override
    public boolean isVipOnly() {
        return vipOnly;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Map<Integer, ICatalogItem> getItems() {
        return items;
    }

    @Override
    public @Nullable ICatalogItem getOffer(int id) {
        return this.items.get(id);
    }

    @Override
    public List<String> getImages() {
        return images;
    }

    @Override
    public List<String> getTexts() {
        return texts;
    }

    @Override
    public String getLinkName() {
        return linkName;
    }

    @Override
    public String getExtraData() {
        return extraData;
    }

    @Override
    public CatalogPageType getType() {
        return type;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public List<ICatalogPage> getChildren() {
        return children;
    }

    @Override
    public void fill(IConnectionResult result) throws Exception {


        this.id = result.getInt("id");
        this.caption = result.getString("caption");
        this.icon = result.getInt("icon_image");
        this.minRank = result.getInt("min_rank");
        this.template = result.getString("page_layout");
        this.parentId = result.getInt("parent_id");
        this.linkName = "";// TODO result.getString("link");
        this.type = CatalogPageType.NORMAL;//CatalogPageType.valueOf(result.getString("type"));
        this.extraData = "";//result.getString("extra_data");
        this.order = result.getInt("order_num");
        this.vipOnly = result.getString("vip_only").equals("1");

        this.images = new ArrayList<>();
        this.images.add(result.getString("page_headline"));
        this.images.add(result.getString("page_teaser"));
        this.images.add(result.getString("page_special"));

        this.texts = new ArrayList<>();
        this.texts.add(result.getString("page_text1"));
        this.texts.add(result.getString("page_text2"));
        this.texts.add(result.getString("page_text_details"));
        this.enabled = result.getString("enabled").equals("1");
    }

    public void serializePageData(OutgoingPacket packet) {
        packet.appendBoolean(this.isEnabled())
                .appendInt(this.getIcon())
                .appendInt(this.isEnabled() ? this.getId() : -1)
                .appendString(this.getCaption())
                .appendString(this.getCaption());

        packet.appendInt(this.getOfferSize()); // TODO
//        for (int i : getOfferSize().toArray()) {
//            packet.appendInt(i);
//        }

        packet.appendInt(this.getChildren().size());
        for (var page : this.getChildren()) {
            page.serializePageData(packet);
        }
    }

    @Override
    public OutgoingPacket serializeItems(OutgoingPacket packet, IHabbo habbo) {
        packet.appendInt(this.getItems().size());
        for (var item : this.getItems().values()) {
            item.serialize(packet);
        }
        return packet;
    }

    @Override
    public abstract void serialize(OutgoingPacket packet);

    public void serializeExtra(OutgoingPacket packet) {

    }
}
