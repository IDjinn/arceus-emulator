package encoders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import networking.packets.OutgoingPacket;

public class GameServerMessageEncoder extends MessageToByteEncoder<OutgoingPacket> {

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
