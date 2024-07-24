package packets.incoming.rooms;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.rooms.IRoomManager;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
import networking.packets.IncomingEvent;
import org.jetbrains.annotations.Nullable;
import packets.incoming.IncomingHeaders;
import packets.outgoing.rooms.prepare.HotelViewComposer;

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
    public void parse(IIncomingPacket packet, IClient client) {
        var id = packet.readInt();
        @Nullable var password = packet.readString();

        var room = this.roomManager.tryLoadRoom(id);

        if (room == null) {
            client.sendMessage(new HotelViewComposer());
            return;
        }

        room.prepareForHabbo(client.getHabbo(), password);
    }
}
