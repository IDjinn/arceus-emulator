package packets.outgoing.catalog;

import habbo.catalog.pages.ICatalogPage;
import habbo.habbos.IHabbo;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

import java.util.List;

public class CatalogIndexComposer extends OutgoingPacket {
    public CatalogIndexComposer(IHabbo habbo, String mode, List<ICatalogPage> pages) {
        super(OutgoingHeaders.CatalogPagesListComposer);
        appendBoolean(true); // isVisible
        appendInt(0); // icon
        appendInt(-1); // page id
        appendString("root"); // caption
        appendString(""); // localization
        appendInt(0); // offers

        appendInt(pages.size());
        for (var page : pages) {
            page.serializePageData(this);
        }

        appendBoolean(false);
        appendString(mode);
    }
}
