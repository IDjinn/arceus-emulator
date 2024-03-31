package networking.packets.incoming.guest;

import io.netty.channel.ChannelHandlerContext;
import networking.packets.IncomingPacket;
import networking.packets.incoming.IncomingEvent;

public class PingEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return 2596;
    }

    @Override
    public void ParseForGuest(IncomingPacket packet, ChannelHandlerContext ctx) {

    }
}
