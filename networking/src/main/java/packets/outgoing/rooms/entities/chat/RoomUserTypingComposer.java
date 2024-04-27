package packets.outgoing.rooms.entities.chat;

import habbo.rooms.entities.IRoomEntity;
import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class RoomUserTypingComposer extends OutgoingPacket {
    public RoomUserTypingComposer(IRoomEntity entity, boolean isTyping) {
        super(OutgoingHeaders.RoomUserTypingComposer);

        this.appendInt(entity.getVirtualId());
        this.appendInt(isTyping ? 1 : 0);
    }
}
