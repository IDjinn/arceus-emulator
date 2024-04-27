package encoders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import packets.outgoing.OutgoingPacket;

public class OutgoingPacketEncoder extends MessageToByteEncoder<OutgoingPacket> {

    @Override
    public boolean acceptOutboundMessage(Object msg) throws Exception {
        return super.acceptOutboundMessage(msg);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, OutgoingPacket message, ByteBuf out) throws Exception {
        var buf = message.getBuffer();

        try {
            out.writeBytes(buf);
        } finally {
            // Release copied buffer.
            buf.release();
        }
    }
}
