package packets.dto.outgoing.catalog;

import habbo.catalog.items.ICatalogItem;
import networking.packets.IPacketDTO;

public record CatalogPurchaseOkComposerDTO(ICatalogItem catalogItem) implements IPacketDTO {
    public static CatalogPurchaseOkComposerDTO of(ICatalogItem item) {
        return new CatalogPurchaseOkComposerDTO(item);
    }
}
