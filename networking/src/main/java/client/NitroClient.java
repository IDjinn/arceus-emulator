package client;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;
import networking.client.INitroClient;
import networking.packets.OutgoingPacket;

import java.util.List;

public class NitroClient implements INitroClient {
    private final ChannelHandlerContext ctx;

    @Inject
    public NitroClient(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }


    @Override
    public void sendMessage(OutgoingPacket packet) {
        ctx.channel().write(packet.getBuffer());
    }

    @Override
    public void sendMessages(List<OutgoingPacket> messages) {
        for (var message : messages) {
            ctx.channel().write(message.getBuffer());
        }
        ctx.channel().flush();
    }
}