package encoders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import networking.packets.IOutgoingPacket;

public class OutgoingPacketEncoder extends MessageToByteEncoder<IOutgoingPacket<U>> {

    @Override
    public boolean acceptOutboundMessage(Object msg) throws Exception {
        return super.acceptOutboundMessage(msg);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, IOutgoingPacket<U> message, ByteBuf out) throws Exception {
        var buf = message.getBuffer();

        try {
            out.writeBytes(buf);
        } finally {
            // Release copied buffer.
            buf.release();
        }
    }
}
