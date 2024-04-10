package packets.incoming.navigator;

import networking.client.INitroClient;
import networking.packets.IIncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;
import packets.outgoing.navigator.CanCreateRoomComposer;

public class RequestCanCreateRoomEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestCanCreateRoomEvent;
    }

    @Override
    public void parse(IIncomingPacket packet, INitroClient client) {
        client.sendMessage(new CanCreateRoomComposer());
    }
}
