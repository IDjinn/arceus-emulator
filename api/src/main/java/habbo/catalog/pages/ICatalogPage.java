package habbo.catalog.pages;

import habbo.catalog.items.ICatalogItem;
import habbo.habbos.IHabbo;
import networking.packets.OutgoingPacket;
import networking.util.ISerializable;
import org.jetbrains.annotations.Nullable;
import utils.IFillable;

import java.util.List;
import java.util.Map;

public interface ICatalogPage extends ISerializable, IFillable {
    int getOfferSize();

    int getId();

    String getCaption();

    int getIcon();

    int getMinRank();

    String getTemplate();

    int getParentId();

    boolean isVipOnly();

    boolean isEnabled();

    Map<Integer, ICatalogItem> getItems();

    @Nullable
    ICatalogItem
    getOffer(int id);

    List<String> getImages();

    List<String> getTexts();

    String getLinkName();

    String getExtraData();

    CatalogPageType getType();

    int getOrder();

    List<ICatalogPage> getChildren();

    public void serializePageData(OutgoingPacket packet);

    OutgoingPacket serializeItems(OutgoingPacket packet, IHabbo habbo);

    void serializeExtra(OutgoingPacket packet);
}
