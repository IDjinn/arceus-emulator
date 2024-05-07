package networking.packets;

import io.netty.channel.ChannelHandlerContext;
import networking.client.IClient;

public abstract class IncomingEvent {
    public abstract int getHeaderId();

    public void parse(IIncomingPacket packet, IClient client) {
        
    }

    public void parseForGuest(IIncomingPacket packet, ChannelHandlerContext ctx) {
        throw new RuntimeException("Should not be called by base class, it does means it was not overridden by derived class and suggested an implementation error");
    }
}
