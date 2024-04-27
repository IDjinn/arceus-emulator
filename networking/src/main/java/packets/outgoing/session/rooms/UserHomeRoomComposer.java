package packets.outgoing.session.rooms;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class UserHomeRoomComposer extends OutgoingPacket {
    public UserHomeRoomComposer(int homeRoom, int roomToEnter) {
        super(OutgoingHeaders.UserHomeRoomComposer);
        appendInt(homeRoom);
        appendInt(roomToEnter);
    }
}