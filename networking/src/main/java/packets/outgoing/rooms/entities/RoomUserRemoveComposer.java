package packets.outgoing.rooms.entities;

import habbo.rooms.entities.IRoomEntity;
import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class RoomUserRemoveComposer extends OutgoingPacket {
    public RoomUserRemoveComposer(IRoomEntity roomEntity) {
        super(OutgoingHeaders.RoomUserRemoveComposer);
        this.appendInt(roomEntity.getVirtualId());
    }
}
