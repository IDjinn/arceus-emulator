package networking.packets.incoming.guest;

import io.netty.channel.ChannelHandlerContext;
import networking.packets.IncomingPacket;
import networking.packets.incoming.IncomingEvent;

public class SecureLoginEvent extends IncomingEvent {

    @Override
    public int getHeaderId() {
        return 2419;
    }

    @Override
    public void ParseForGuest(IncomingPacket packet, ChannelHandlerContext ctx) {

    }
}
