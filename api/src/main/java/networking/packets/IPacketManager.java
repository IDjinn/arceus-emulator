package networking.packets;

import io.netty.channel.ChannelHandlerContext;
import networking.client.IClient;
import networking.packets.incoming.IIncomingEvent;
import networking.packets.incoming.IIncomingPacket;
import networking.packets.outgoing.IOutgoingEvent;

import java.util.Optional;

public interface IPacketManager {
    boolean isParallelParsingEnabled();

    boolean isLoggingEnabled();

    void registerIncoming(IIncomingEvent IIncomingEvent);

    String getIncomingEventName(int headerId);

    void parse(IIncomingPacket IIncomingPacket, IClient client);

    void parseForGuest(IIncomingPacket IIncomingPacket, ChannelHandlerContext channel);

    Optional<IOutgoingEvent<IPacketDTO>> getOugoingFromDTOClazz(Class<? extends IPacketDTO> clazz);
}
