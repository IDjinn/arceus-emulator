package packets.incoming.chat;

import com.google.inject.Singleton;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;

@Singleton
public class RoomUserTalkEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RoomUserTalkEvent;
    }

    @Override
    public void parse(final IncomingPacket packet, final INitroClient client) {
        var message = packet.readString((short) 10);
    }
}
