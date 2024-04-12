package packets.outgoing.rooms.entities.chat;

import habbo.rooms.entities.IRoomEntity;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class RoomUserTypingComposer extends OutgoingPacket {
    public RoomUserTypingComposer(IRoomEntity entity, boolean isTyping) {
        super(OutgoingHeaders.RoomUserTypingComposer);

        this.appendInt(entity.getVirtualId());
        this.appendInt(isTyping ? 1 : 0);
    }
}
