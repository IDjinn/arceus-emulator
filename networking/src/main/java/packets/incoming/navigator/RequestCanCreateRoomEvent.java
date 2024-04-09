package packets.incoming.navigator;

import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;
import packets.outgoing.navigator.CanCreateRoomComposer;

public class RequestCanCreateRoomEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestCanCreateRoomEvent;
    }

    @Override
    public void parse(IncomingPacket packet, INitroClient client) {
        client.sendMessage(new CanCreateRoomComposer());
    }
}
