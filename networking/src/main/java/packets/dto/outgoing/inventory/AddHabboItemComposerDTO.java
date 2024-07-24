package packets.dto.outgoing.inventory;

import networking.packets.IPacketDTO;

import java.util.List;

public record AddHabboItemComposerDTO(AddHabboItemCategory category, List<Integer> itemIds) implements IPacketDTO {
}
