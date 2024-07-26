package packets.dto.outgoing.inventory;

import networking.packets.IPacketDTO;

import java.util.List;

public record AddHabboItemComposerDTO(AddHabboItemCategory category, List<Integer> itemIds) implements IPacketDTO {
    public static AddHabboItemComposerDTO of(AddHabboItemCategory category, List<Integer> itemIds){
            return new AddHabboItemComposerDTO(category, itemIds);
    }
}
