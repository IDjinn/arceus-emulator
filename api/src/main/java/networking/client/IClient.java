package networking.client;

import habbo.habbos.IHabbo;
import io.netty.channel.ChannelHandlerContext;
import networking.packets.IOutgoingPacket;
import org.jetbrains.annotations.NotNull;
import utils.result.GameError;

public interface IClient {
    ChannelHandlerContext getContext();

    void sendError(GameError error);

    void sendErrors(GameError... errors);

    void sendMessage(IOutgoingPacket packet);

    void sendMessages(IOutgoingPacket... messages);

    void flush();

    IHabbo getHabbo();

    @NotNull
    void setHabbo(IHabbo habbo);

    void dispose();
}
