package networking.packets;

import io.netty.channel.ChannelHandlerContext;
import networking.client.IClient;

public interface IPacketManager {
    boolean isParallelParsingEnabled();

    boolean isLoggingEnabled();

    void registerIncoming(IIncomingEvent IIncomingEvent);

    String getIncomingEventName(int headerId);

    void parse(IIncomingPacket IIncomingPacket, IClient client);

    void parseForGuest(IIncomingPacket IIncomingPacket, ChannelHandlerContext channel);
}
