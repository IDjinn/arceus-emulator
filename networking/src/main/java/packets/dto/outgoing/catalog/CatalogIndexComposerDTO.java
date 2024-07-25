package packets.dto.outgoing.catalog;

import habbo.catalog.pages.ICatalogPage;
import networking.packets.IPacketDTO;

import java.util.List;

public record CatalogIndexComposerDTO(boolean showId, String mode, List<? extends ICatalogPage> pages) implements IPacketDTO {
}
