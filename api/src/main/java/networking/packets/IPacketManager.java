package networking.packets;

import io.netty.channel.ChannelHandlerContext;
import networking.client.IClient;

public interface IPacketManager {
    boolean isParallelParsingEnabled();

    boolean isLoggingEnabled();

    String getIncomingEventName(int headerId);

    void parse(IIncomingPacket IIncomingPacket, IClient client);

    void parseForGuest(IIncomingPacket IIncomingPacket, ChannelHandlerContext channel);
}
