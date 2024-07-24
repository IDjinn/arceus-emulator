package decoders;

import com.google.inject.Inject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import networking.packets.IIncomingPacketProvider;

import java.util.List;

public class GameByteDecoder extends ByteToMessageDecoder {
    @Inject
    private IIncomingPacketProvider incomingPacketProvider;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        final var header = in.readShort();
        final var body = Unpooled.copiedBuffer(in.readBytes(in.readableBytes()));
        out.add(this.incomingPacketProvider.createIncomingPacket(header, body, ctx));
    }
}