package packets.outgoing.catalog;

import habbo.catalog.pages.ICatalogPage;
import habbo.habbos.IHabbo;
import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class CatalogPageComposer extends OutgoingPacket {
    public CatalogPageComposer(ICatalogPage page, IHabbo habbo, int offerId, String mode) {
        super(OutgoingHeaders.CatalogPageComposer);
        appendInt(page.getId());
        appendString(mode);

        page.serialize(this);
        page.serializeItems(this, habbo);
        appendInt(offerId);
        appendBoolean(false);
        page.serializeExtra(this);
    }
}