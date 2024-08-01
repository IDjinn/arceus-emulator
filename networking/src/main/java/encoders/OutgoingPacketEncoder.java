package encoders;

import com.google.inject.Inject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import networking.packets.IPacketDTO;
import networking.packets.IPacketManager;
import networking.packets.IPacketWriterFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OutgoingPacketEncoder extends MessageToByteEncoder<IPacketDTO> {
    private static final Logger LOGGER = LogManager.getLogger();
    private @Inject IPacketManager packetManager;
    private @Inject IPacketWriterFactory packetWriterProvider;
    
    
    @Override
    public boolean acceptOutboundMessage(Object msg) throws Exception {
        return super.acceptOutboundMessage(msg);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, IPacketDTO packetDTO, ByteBuf byteBuf) throws Exception {
        final var outgoing = this.packetManager.getOugoingFromDTOClazz(packetDTO.getClass());
        if (outgoing.isEmpty()) {
            LOGGER.warn(STR."Outgoing packet not found for DTO class \{packetDTO.getClass().getSimpleName()}");
            return;
        }
        
        final var outgoingEvent = outgoing.get();
        final var packetWriter = this.packetWriterProvider.create(Unpooled.buffer());
        outgoingEvent.encode(packetWriter, packetDTO);

        if (!this.packetManager.isLoggingEnabled()) return;
        
        try {
            LOGGER.debug("[outgoing <-] {} packet {} [{}]",
                    outgoingEvent.getHeaderId(),
                    outgoingEvent.getClass().getSimpleName(),
                    packetWriter.getBuffer());
        } catch (Exception e) {
            LOGGER.error("error while decoding packet {}", outgoingEvent.getHeaderId(), e);
        }
    }
}
