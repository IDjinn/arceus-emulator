package networking.client;

import habbo.habbos.IHabbo;
import io.netty.channel.ChannelHandlerContext;
import networking.packets.IPacketDTO;
import networking.packets.outgoing.IOutgoingEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public interface IClient {
    ChannelHandlerContext getContext();

    void sendMessage(IPacketDTO payload);

    void sendMessages(Collection<IPacketDTO> payloads);
    
    void sendMessages(IPacketDTO... payloads);

    void flush();

    IHabbo getHabbo();

    void setHabbo(IHabbo habbo);

    void dispose();
}