package packets.dto.outgoing.inventory;

import habbo.habbos.inventory.IHabboInventoryItem;
import networking.packets.IPacketDTO;

import java.util.List;

public record InventoryItemsComposerDTO(int fragment, int totalFragments, List<IHabboInventoryItem> items) implements IPacketDTO {
}
