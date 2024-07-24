package networking.client;

import habbo.habbos.IHabbo;
import io.netty.channel.ChannelHandlerContext;
import networking.packets.IOutgoingPacket;
import networking.packets.IPacketDTO;

import java.util.List;
import java.util.Objects;

public interface IClient {
    ChannelHandlerContext getContext();

    void sendMessage(IOutgoingPacket<?> packet);

    void sendMessages(List<IOutgoingPacket<?>> messages);

    void sendMessages(IOutgoingPacket<?>... messages);

    <T extends IPacketDTO> void sendMessage(Class<IOutgoingPacket<T>> type, Objects... parameters);

    <T extends IPacketDTO> void sendMessage(int header, T payload);

    void sendMessages(IPacketDTO... payloads);
    
    void flush();

    IHabbo getHabbo();

    void setHabbo(IHabbo habbo);

    void dispose();
}