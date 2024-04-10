package decoders;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import networking.packets.IIncomingPacket;
import networking.util.GameServerAttributes;

import java.util.List;

public class GameMessageRateLimit extends MessageToMessageDecoder<IIncomingPacket> {

    private static final int RESET_TIME = 1;
    private static final int MAX_COUNTER = 10;

    @Override
    protected void decode(ChannelHandlerContext ctx, IIncomingPacket message, List<Object> out) throws Exception {
        var client = ctx.channel().attr(GameServerAttributes.CLIENT).get();
        if (client == null) {
            return;
        }


        // TODO: RATE LIMIT
        out.add(message);
    }

}
