package networking.packets;

import io.netty.channel.ChannelHandlerContext;
import networking.client.INitroClient;

public interface IPacketManager {
    public String getIncomingEventName(int headerId);
    public String getOutgoingEventName(int headerId);
    public void Parse(IncomingPacket incomingPacket, INitroClient client);
    
    public void ParseForGuest(IncomingPacket incomingPacket, ChannelHandlerContext channel);
}
