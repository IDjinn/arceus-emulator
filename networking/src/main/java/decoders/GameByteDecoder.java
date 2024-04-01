package decoders;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import networking.packets.IncomingPacket;

import java.util.List;

public class GameByteDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        short header = in.readShort();
        ByteBuf body = Unpooled.copiedBuffer(in.readBytes(in.readableBytes()));
        out.add(new IncomingPacket(header, body));
    }
}