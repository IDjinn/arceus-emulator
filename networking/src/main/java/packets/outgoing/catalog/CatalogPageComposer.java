package packets.outgoing.catalog;

import habbo.catalog.pages.ICatalogPage;
import habbo.habbos.IHabbo;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class CatalogPageComposer extends OutgoingPacket {
    public CatalogPageComposer(ICatalogPage page, IHabbo habbo, int offerId, String mode) {
        super(OutgoingHeaders.CatalogPageComposer);
        this.appendInt(page.getId());
        this.appendString(mode);

        page.serialize(this);
        page.serializeItems(this, habbo);
        this.appendInt(offerId);
        this.appendBoolean(false, "acceptSeasonCurrencyAsCredits");
        page.serializeExtra(this);
    }
}