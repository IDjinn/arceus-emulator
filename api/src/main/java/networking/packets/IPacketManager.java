package networking.packets;

import io.netty.channel.ChannelHandlerContext;
import networking.client.INitroClient;

public interface IPacketManager {
    public boolean isParallelParsingEnabled();

    public boolean isLoggingEnabled();
    public String getIncomingEventName(int headerId);

    public void parse(IncomingPacket incomingPacket, INitroClient client);

    public void parseForGuest(IncomingPacket incomingPacket, ChannelHandlerContext channel);
}
