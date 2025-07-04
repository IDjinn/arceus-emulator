package packets.incoming.rooms;

import com.google.inject.Singleton;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
import networking.packets.IncomingEvent;
import packets.incoming.IncomingHeaders;

@Singleton
public class RequestRoomHeightmapEvent extends IncomingEvent {

    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestHeightmapEvent;
    }

    @Override
    public void parse(IIncomingPacket packet, IClient client) {
        if (client.getHabbo().getEntity() == null) return;

        client.getHabbo().getEntity().getRoom().join(client.getHabbo());
    }
}
