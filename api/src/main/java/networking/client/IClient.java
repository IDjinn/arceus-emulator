package networking.client;

import habbo.habbos.IHabbo;
import io.netty.channel.ChannelHandlerContext;
import networking.packets.OutgoingPacket;
import networking.packets.PacketDTO;

import java.util.List;
import java.util.Objects;

public interface IClient {
    ChannelHandlerContext getContext();

    void sendMessage(OutgoingPacket<?> packet);

    void sendMessages(List<OutgoingPacket<?>> messages);

    void sendMessages(OutgoingPacket<?>... messages);

    <T extends PacketDTO> void sendMessage(Class<OutgoingPacket<T>> type, Objects... parameters);

    <T extends PacketDTO> void sendMessage(int header, T payload);
    
    void flush();

    IHabbo getHabbo();

    void setHabbo(IHabbo habbo);

    void dispose();
}