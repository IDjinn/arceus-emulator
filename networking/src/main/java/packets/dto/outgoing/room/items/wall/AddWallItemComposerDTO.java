package packets.dto.outgoing.room.items.wall;

import habbo.rooms.components.objects.items.floor.IFloorItem;
import habbo.rooms.components.objects.items.wall.IWallItem;
import networking.packets.IPacketDTO;

public record AddWallItemComposerDTO(IWallItem wallItem) implements IPacketDTO {
}
