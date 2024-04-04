package habbo.catalog.page.layouts;

import habbo.catalog.page.CatalogPage;
import networking.packets.OutgoingPacket;

public class DefaultCatalogPage extends CatalogPage {

    @Override
    public void serialize(OutgoingPacket packet) {
        packet.appendString("default_3x3");
        packet.appendInt(0); // images size
//        packet.appendString("getHeaderImage()");
//        packet.appendString("getTeaserImage()");
//        packet.appendString("getSpecialImage()");
        packet.appendInt(0); // texts
//        packet.appendString("getTextOne()");
//        packet.appendString("getTextDetails()");
//        packet.appendString("getTextTeaser()");
    }

}
