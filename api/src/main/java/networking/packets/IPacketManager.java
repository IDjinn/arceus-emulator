package networking.packets;

import io.netty.channel.ChannelHandlerContext;
import networking.client.INitroClient;

public interface IPacketManager {
    public boolean isParallelParsingEnabled();

    public boolean isLoggingEnabled();
    public String getIncomingEventName(int headerId);

    public void parse(IIncomingPacket IIncomingPacket, INitroClient client);

    public void parseForGuest(IIncomingPacket IIncomingPacket, ChannelHandlerContext channel);
}
