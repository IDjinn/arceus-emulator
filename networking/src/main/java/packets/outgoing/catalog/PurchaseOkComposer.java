package packets.outgoing.catalog;

import habbo.catalog.items.ICatalogItem;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class PurchaseOkComposer extends OutgoingPacket {

    public PurchaseOkComposer(ICatalogItem item) { // TODO NON ITEM PURCHASE
        super(OutgoingHeaders.PurchaseOKComposer);
        appendInt(item.getId());
        appendString(item.getFurniture().getItemName());
        appendBoolean(false);// rent
        appendInt(item.getCostCredits());
        appendInt(item.getCostActivityPoints());
        appendInt(0); // points type
        appendBoolean(false); // is gift

        appendInt(1); // total items
        {
            appendString(item.getFurniture().getType().toString());
            appendInt(item.getFurniture().getSpriteId());
            appendString(""); // extradata
            appendInt(1); // count
            appendString("");
            appendInt(1); // is limited?
        }
    }

    public PurchaseOkComposer(int group) {
        super(OutgoingHeaders.PurchaseOKComposer); // TODO GROUPS


        appendInt(6165);
        appendString("CREATE_GUILD");
        appendBoolean(false);
        appendInt(10);
        appendInt(0);
        appendInt(0);
        appendBoolean(true);
        appendInt(0);
        appendInt(2);
        appendBoolean(false);
    }
}
