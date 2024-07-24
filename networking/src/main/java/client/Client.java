package client;

import habbo.habbos.IHabbo;
import io.netty.channel.ChannelHandlerContext;
import networking.client.IClient;
import networking.packets.OutgoingPacket;

import java.util.List;

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
    public void sendMessage(OutgoingPacket<U> packet) {
        this.ctx.channel().write(packet);
        this.ctx.channel().flush();
    }

    @Override
    public void sendMessages(List<OutgoingPacket<U>> messages) {
        for (var message : messages) {
            this.ctx.channel().write(message);
        }

        this.ctx.channel().flush();
    }

    @Override
    public void sendMessages(OutgoingPacket<U>... messages) {
        for (var message : messages) {
            this.ctx.channel().write(message);
        }

        this.ctx.channel().flush();
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
    public IHabbo getHabbo() {
        return this.habbo;
    }

    @Override
    public void dispose() {
        this.ctx.channel().disconnect();
    }
}