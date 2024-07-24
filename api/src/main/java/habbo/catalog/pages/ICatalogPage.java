package habbo.catalog.pages;

import habbo.catalog.items.ICatalogItem;
import habbo.habbos.IHabbo;
import networking.packets.IOutgoingPacket;
import org.jetbrains.annotations.Nullable;
import utils.interfaces.IFillable;

import java.util.List;
import java.util.Map;

public interface ICatalogPage extends IFillable {
    int getOfferSize();

    int getId();

    String getCaption();

    int getIcon();

    int getMinRank();

    String getTemplate();

    int getParentId();

    boolean isVipOnly();

    boolean isEnabled();

    boolean isVisible();

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

    IOutgoingPacket<U> serializeItems(IOutgoingPacket<U> packet, IHabbo habbo);

    void serializeExtra(IOutgoingPacket<U> packet);
}
