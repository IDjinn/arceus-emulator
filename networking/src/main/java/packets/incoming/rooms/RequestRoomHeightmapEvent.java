package packets.incoming.rooms;

import com.google.inject.Singleton;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;

@Singleton
public class RequestRoomHeightmapEvent extends IncomingEvent {

    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestHeightmapEvent;
    }

    @Override
    public void Parse(IncomingPacket packet, INitroClient client) {
        if (client.getHabbo().getPlayerEntity() == null) return;

        client.getHabbo().getPlayerEntity().getRoom().join(client.getHabbo());
    }
}
