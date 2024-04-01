package networking.packets.outgoing.session.rooms;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class UserHomeRoomComposer extends OutgoingPacket {
    public UserHomeRoomComposer(int homeRoom, int roomToEnter) {
        super(OutgoingHeaders.UserHomeRoomComposer);
        appendInt(homeRoom);
        appendInt(roomToEnter);
    }
}