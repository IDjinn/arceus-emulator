package packets.incoming.navigator;

import networking.client.IClient;
import networking.packets.IIncomingPacket;
import networking.packets.IncomingEvent;
import packets.incoming.IncomingHeaders;
import packets.outgoing.navigator.CanCreateRoomComposer;

public class RequestCanCreateRoomEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestCanCreateRoomEvent;
    }

    @Override
    public void parse(IIncomingPacket packet, IClient client) {
        client.sendMessage(new CanCreateRoomComposer());
    }
}
