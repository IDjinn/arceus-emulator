package encoders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class OutgoingPacketEncoder extends MessageToByteEncoder<IOutgoingDTOSerializer<U>> {

    @Override
    public boolean acceptOutboundMessage(Object msg) throws Exception {
        return super.acceptOutboundMessage(msg);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, IOutgoingDTOSerializer<U> message, ByteBuf out) throws Exception {
        var buf = message.getBuffer();

        try {
            out.writeBytes(buf);
        } finally {
            // Release copied buffer.
            buf.release();
        }
    }
}
