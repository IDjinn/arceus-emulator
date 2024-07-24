package packets.outgoing.catalog;

import habbo.catalog.items.ICatalogItem;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class PurchaseOkComposer extends OutgoingPacket {

    public PurchaseOkComposer(ICatalogItem item) { // TODO NON ITEM PURCHASE
        super(OutgoingHeaders.PurchaseOKComposer);
        this.appendInt(item.getId());
        this.appendString(item.getFurniture().getItemName());
        this.appendBoolean(false);// rent
        this.appendInt(item.getCostCredits());
        this.appendInt(item.getCostActivityPoints());
        this.appendInt(0); // points type
        this.appendBoolean(false); // is gifted

        this.appendInt(1); // total items
        {
            this.appendString(item.getFurniture().getType().toString());
            this.appendInt(item.getFurniture().getSpriteId());
            this.appendString(""); // extradata
            this.appendInt(1); // count
            this.appendString("");
            this.appendInt(1); // is limited?
        }
    }

    public PurchaseOkComposer(int group) {
        super(OutgoingHeaders.PurchaseOKComposer); // TODO GROUPS


        this.appendInt(6165);
        this.appendString("CREATE_GUILD");
        this.appendBoolean(false);
        this.appendInt(10);
        this.appendInt(0);
        this.appendInt(0);
        this.appendBoolean(true);
        this.appendInt(0);
        this.appendInt(2);
        this.appendBoolean(false);
    }
}
