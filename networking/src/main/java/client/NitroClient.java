package client;

import habbo.habbos.IHabbo;
import io.netty.channel.ChannelHandlerContext;
import networking.client.INitroClient;
import networking.packets.OutgoingPacket;

import java.util.List;

public class NitroClient implements INitroClient {
    private final ChannelHandlerContext ctx;
    private IHabbo habbo;

    public NitroClient(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public ChannelHandlerContext getContext() {
        return this.ctx;
    }

    @Override
    public void sendMessage(OutgoingPacket packet) {
        this.ctx.channel().write(packet);
        this.ctx.channel().flush();
    }

    @Override
    public void sendMessages(List<OutgoingPacket> messages) {
        for (var message : messages) {
            this.ctx.channel().write(message);
        }

        this.ctx.channel().flush();
    }

    @Override
    public void sendMessages(OutgoingPacket... messages) {
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
        return habbo;
    }
}