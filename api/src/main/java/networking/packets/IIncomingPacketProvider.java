package networking.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface IIncomingPacketProvider {

    IIncomingPacket createIncomingPacket(int packetId, ByteBuf buffer, ChannelHandlerContext ctx);
}
