package packets.dto.outgoing.room.items.wall;

import habbo.rooms.components.objects.items.wall.IWallItem;
import networking.packets.IPacketDTO;

import java.util.Collection;
import java.util.List;

public record RoomWallItemsComposerDTO(List<String> owners, Collection<? extends IWallItem> allItems) implements IPacketDTO {
}
