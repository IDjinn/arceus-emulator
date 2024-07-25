package packets.dto.outgoing.room.items.floor;

import habbo.rooms.components.objects.items.floor.IFloorItem;
import networking.packets.IPacketDTO;

public record RemoveFloorItemComposerDTO(IFloorItem floorItem, int pickupPlayerId, int delay) implements IPacketDTO {
}
