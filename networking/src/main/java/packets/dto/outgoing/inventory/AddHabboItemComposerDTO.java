package packets.dto.outgoing.inventory;

import networking.packets.IPacketDTO;

import java.util.List;

public record AddHabboItemComposerDTO(AddHabboItemCategoryComposerDTO category, List<Integer> itemIds) implements IPacketDTO {
}
