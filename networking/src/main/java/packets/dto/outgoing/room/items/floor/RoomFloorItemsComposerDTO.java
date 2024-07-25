package packets.dto.outgoing.room.items.floor;

import habbo.rooms.components.objects.items.floor.IFloorItem;
import habbo.rooms.components.objects.items.wall.IWallItem;
import networking.packets.IPacketDTO;

import java.util.Collection;
import java.util.List;

public record RoomFloorItemsComposerDTO(List<String> owners, Collection<? extends IFloorItem> allItems) implements IPacketDTO {
}
