package networking.packets.incoming.guest;

import com.google.inject.Singleton;
import io.netty.channel.ChannelHandlerContext;
import networking.packets.IncomingPacket;
import networking.packets.incoming.IncomingEvent;
import networking.packets.incoming.IncomingHeaders;
import networking.util.NoAuth;

@Singleton
@NoAuth
public class PingEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.PingEvent;
    }

    @Override
    public void ParseForGuest(IncomingPacket packet, ChannelHandlerContext ctx) {

    }
}
