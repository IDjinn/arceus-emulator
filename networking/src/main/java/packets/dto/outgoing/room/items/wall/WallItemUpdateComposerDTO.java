package packets.dto.outgoing.room.items.wall;

import habbo.rooms.components.objects.items.wall.IWallItem;
import networking.packets.IPacketDTO;

public record WallItemUpdateComposerDTO(IWallItem wallItem) implements IPacketDTO {
}
