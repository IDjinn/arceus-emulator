package client;

import habbo.habbos.IHabbo;
import io.netty.channel.ChannelHandlerContext;
import networking.client.IClient;
import networking.packets.IOutgoingPacket;
import org.jetbrains.annotations.NotNull;
import utils.result.GameError;

public class Client implements IClient {
    private final ChannelHandlerContext ctx;
    private IHabbo habbo;

    public Client(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public ChannelHandlerContext getContext() {
        return this.ctx;
    }

    @Override
    public void sendError(final GameError error) {
        // TODO
    }

    @Override
    public void sendErrors(final GameError... errors) {
        for (final var error : errors) {
            this.sendError(error);
        }
    }

    @Override
    public void sendMessage(IOutgoingPacket packet) {
        this.ctx.channel().write(packet);
        this.flush();
    }


    @Override
    public void sendMessages(IOutgoingPacket... messages) {
        for (var message : messages) {
            this.ctx.channel().write(message);
        }

        this.flush();
    }

    @Override
    public void flush() {
        this.ctx.channel().flush();
    }

    @Override
    public void setHabbo(IHabbo habbo) {
        this.habbo = habbo;
    }

    @Override
    @NotNull
    public IHabbo getHabbo() {
        return this.habbo;
    }

    @Override
    public void dispose() {
        this.ctx.channel().disconnect();
    }
}