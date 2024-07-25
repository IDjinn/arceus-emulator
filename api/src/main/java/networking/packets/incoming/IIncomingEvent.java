package networking.packets.incoming;

import io.netty.channel.ChannelHandlerContext;
import networking.client.IClient;

public interface IIncomingEvent {
    int getHeaderId();

    void parse(IIncomingPacket packet, IClient client);

    default void parseForGuest(IIncomingPacket packet, ChannelHandlerContext ctx) {
        
    }
}
