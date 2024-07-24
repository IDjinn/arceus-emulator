package packets.dto.outgoing.inventory;

import networking.packets.IPacketDTO;

public record RemoveHabboItemComposerDTO(int itemId) implements IPacketDTO {
}
