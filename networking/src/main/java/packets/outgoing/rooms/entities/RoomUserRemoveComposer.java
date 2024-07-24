package packets.outgoing.rooms.entities;

import habbo.rooms.entities.IRoomEntity;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class RoomUserRemoveComposer extends OutgoingPacket {
    public RoomUserRemoveComposer(IRoomEntity roomEntity) {
        super(OutgoingHeaders.RoomUserRemoveComposer);
        this.appendInt(roomEntity.getVirtualId());
    }
}
