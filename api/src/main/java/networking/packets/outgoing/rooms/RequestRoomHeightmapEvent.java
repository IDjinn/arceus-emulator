package networking.packets.outgoing.rooms;

import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import networking.packets.incoming.IncomingEvent;
import networking.packets.incoming.IncomingHeaders;

public class RequestRoomHeightmapEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestRoomHeightmapEvent;
    }

    @Override
    public void Parse(IncomingPacket packet, INitroClient client) {
        if (client.getHabbo().getRoom() == null) return;

        client.getHabbo().getRoom().join(client.getHabbo());
    }
}
