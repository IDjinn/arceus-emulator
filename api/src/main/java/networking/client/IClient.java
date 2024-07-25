package networking.client;

import habbo.habbos.IHabbo;
import io.netty.channel.ChannelHandlerContext;
import networking.packets.IPacketDTO;

import java.util.List;
import java.util.Objects;

public interface IClient {
    ChannelHandlerContext getContext();

    void sendMessage(IOutgoingDTOSerializer<?> packet);

    void sendMessages(List<IOutgoingDTOSerializer<?>> messages);

    void sendMessages(IOutgoingDTOSerializer<?>... messages);

    <T extends IPacketDTO> void sendMessage(Class<IOutgoingDTOSerializer<T>> type, Objects... parameters);

    <T extends IPacketDTO> void sendMessage(int header, T payload);

    void sendMessages(IPacketDTO... payloads);
    
    void flush();

    IHabbo getHabbo();

    void setHabbo(IHabbo habbo);

    void dispose();
}