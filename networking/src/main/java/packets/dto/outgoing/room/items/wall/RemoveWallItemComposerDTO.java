package packets.dto.outgoing.room.items.wall;

import habbo.rooms.components.objects.items.wall.IWallItem;
import networking.packets.IPacketDTO;

public record RemoveWallItemComposerDTO(IWallItem wallItem, int pickupPlayerId) implements IPacketDTO {
}
