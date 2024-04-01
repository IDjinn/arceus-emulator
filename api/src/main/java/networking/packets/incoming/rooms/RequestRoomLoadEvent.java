package networking.packets.incoming.rooms;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbohotel.rooms.IRoomManager;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import networking.packets.incoming.IncomingEvent;
import networking.packets.incoming.IncomingHeaders;
import org.jetbrains.annotations.Nullable;

@Singleton
public class RequestRoomLoadEvent extends IncomingEvent {
    private final IRoomManager roomManager;

    @Inject
    public RequestRoomLoadEvent(IRoomManager roomManager) {
        this.roomManager = roomManager;
    }

    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestRoomLoadEvent;
    }

    @Override
    public void Parse(IncomingPacket packet, INitroClient client) {
        var id = packet.readInt();
        @Nullable var password = packet.readString();

        var room = roomManager.tryLoadRoom(id);
        if (room == null) {
//   TODO:         client.sendMessage(new OutgoingPacket(OutgoingHeaders.RoomLoadFailed));
            return;
        }

        room.prepareForHabbo(client.getHabbo(), password);
    }
}
