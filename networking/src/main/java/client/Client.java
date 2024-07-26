package client;

import habbo.habbos.IHabbo;
import io.netty.channel.ChannelHandlerContext;
import networking.client.IClient;
import networking.packets.IPacketDTO;
import networking.packets.outgoing.IOutgoingEvent;

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
    public void sendMessage(IPacketDTO payload) {
        this.ctx.channel().write(payload);
    }

    @Override
    public void sendMessages(List<IPacketDTO> payloads) {
        for (final var payload : payloads) {
            this.ctx.channel().write(payload);
        }
        this.ctx.channel().flush();
    }

    @Override
    public void sendMessages(IPacketDTO... payloads) {
        for (final var payload : payloads) {
            this.ctx.channel().write(payload);
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