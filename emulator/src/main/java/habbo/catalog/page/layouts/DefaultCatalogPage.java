package habbo.catalog.page.layouts;

import habbo.catalog.page.CatalogPage;
import networking.packets.OutgoingPacket;

public class DefaultCatalogPage extends CatalogPage {

    @Override
    public void serialize(OutgoingPacket<U> packet) {
        packet.appendString("default_3x3");

        packet.appendInt(this.getImages().size());
        for (var image : this.getImages())
            packet.appendString(image);

        packet.appendInt(this.getTexts().size());
        for (var text : this.getTexts())
            packet.appendString(text);
    }

}
