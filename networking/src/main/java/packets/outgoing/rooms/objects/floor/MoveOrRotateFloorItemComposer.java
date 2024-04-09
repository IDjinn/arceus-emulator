package packets.outgoing.rooms.objects.floor;

import habbo.furniture.FurnitureUsagePolicy;
import habbo.rooms.components.objects.items.floor.IFloorItem;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class MoveOrRotateFloorItemComposer extends OutgoingPacket {
    public MoveOrRotateFloorItemComposer(IFloorItem floorItem) {
        super(OutgoingHeaders.FloorItemUpdateComposer);

        floorItem.serializeItemIdentity(this);
        floorItem.serializePosition(this);
        appendInt(1, "gift stuff (extra data as int?)");
        floorItem.getExtraData().serialize(this);
        appendInt(-1, "expires at");
        appendInt(FurnitureUsagePolicy.Everyone.ordinal());
        appendInt(floorItem.getOwnerData().isPresent() ? floorItem.getOwnerData().get().getId() : 0);
        appendString(floorItem.getRoom().getData().isPublic() || floorItem.getOwnerData().isEmpty() ? "admin" : floorItem.getOwnerData().get().getUsername());
    }
}
