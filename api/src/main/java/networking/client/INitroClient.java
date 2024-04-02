package networking.client;

import io.netty.channel.ChannelHandlerContext;
import habbo.habbos.IHabbo;
import networking.packets.OutgoingPacket;

import java.util.List;

public interface INitroClient {
    ChannelHandlerContext getContext();

    void sendMessage(OutgoingPacket packet);

    void sendMessages(List<OutgoingPacket> messages);

    void sendMessages(OutgoingPacket... messages);

    void flush();

    IHabbo getHabbo();

    void setHabbo(IHabbo habbo);
}
