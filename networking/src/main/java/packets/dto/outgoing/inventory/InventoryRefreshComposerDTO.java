package packets.dto.outgoing.inventory;

import networking.packets.IPacketDTO;

public record InventoryRefreshComposerDTO() implements IPacketDTO {
    public static InventoryRefreshComposerDTO of() {
        return new InventoryRefreshComposerDTO();
    }
}
