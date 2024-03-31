package decoders;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import networking.packets.IPacketManager;
import networking.packets.IncomingPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class IncomingPacketLogger extends MessageToMessageDecoder<IncomingPacket> {
    @Inject private IPacketManager packetManager;
    private static final Logger logger = LogManager.getLogger();
    @Override
    protected void decode(ChannelHandlerContext ctx, IncomingPacket packet, List<Object> out) {
        logger.debug("[-> incoming] {} packet {} [{}]",
                packet.getHeader(),
                packetManager.getIncomingEventName(packet.getHeader()),
                packet.getBuffer());

        out.add(packet);
    }
}
