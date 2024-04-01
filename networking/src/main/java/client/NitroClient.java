package client;

import com.google.inject.Inject;
import habbohotel.users.IHabbo;
import habbohotel.users.IHabboFactory;
import io.netty.channel.ChannelHandlerContext;
import networking.client.INitroClient;
import networking.packets.OutgoingPacket;

import java.util.List;

public class NitroClient implements INitroClient {
    private final ChannelHandlerContext ctx;
    private final IHabbo habbo;

    @Inject
    public NitroClient(ChannelHandlerContext ctx, IHabboFactory habboFactory) {
        this.ctx = ctx;
        this.habbo = habboFactory.createHabbo(this, 1, "Djinn");
    }


    @Override
    public void sendMessage(OutgoingPacket packet) {
        ctx.channel().write(packet);
        ctx.channel().flush();
    }

    @Override
    public void sendMessages(List<OutgoingPacket> messages) {
        for (var message : messages) {
            ctx.channel().write(message);
        }
        ctx.channel().flush();
    }

    @Override
    public void sendMessages(OutgoingPacket... messages) {
        for (var message : messages) {
            ctx.channel().write(message);
        }
        ctx.channel().flush();
    }

    @Override
    public void flush() {
        ctx.channel().flush();
    }

    @Override
    public IHabbo getHabbo() {
        return habbo;
    }
}