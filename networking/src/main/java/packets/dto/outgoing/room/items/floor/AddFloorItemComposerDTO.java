package packets.dto.outgoing.room.items.floor;

import habbo.rooms.components.objects.items.floor.IFloorItem;
import networking.packets.IPacketDTO;

public record AddFloorItemComposerDTO(IFloorItem floorItem) implements IPacketDTO {
}
