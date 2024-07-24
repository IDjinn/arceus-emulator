package packets.dto.outgoing.catalog;

import habbo.catalog.pages.ICatalogPage;
import habbo.habbos.IHabbo;
import networking.packets.IPacketDTO;

public record CatalogPageComposerDTO (ICatalogPage page, IHabbo habbo, int offerId, String mode) implements IPacketDTO {
}
