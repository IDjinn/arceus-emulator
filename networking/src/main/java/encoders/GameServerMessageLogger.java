package encoders;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import networking.packets.IPacketManager;
import networking.packets.OutgoingPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GameServerMessageLogger extends MessageToMessageEncoder<OutgoingPacket> {

    private static final Logger logger = LogManager.getLogger();
    @Inject private IPacketManager packetManager;

    @Override
    protected void encode(ChannelHandlerContext ctx, OutgoingPacket message, List<Object> out) {
        logger.debug("[-> outgoing] {} packet {} [{}",
                message.getHeader(),
                packetManager.getOutgoingEventName(message.getHeader()),
                message.getBuffer());
        out.add(message);
    }

}