package packets.incoming;

import com.google.inject.Inject;
import com.google.inject.Injector;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import networking.packets.IIncomingPacket;
import networking.packets.IIncomingPacketProvider;

public class IncomingPacketProvider implements IIncomingPacketProvider {
    @Inject
    private Injector injector;

    @Override
    public IIncomingPacket createIncomingPacket(final int packetId, final ByteBuf buffer, final ChannelHandlerContext ctx) {
        final var packet = new IncomingPacket(packetId, buffer, ctx);
        this.injector.injectMembers(packet);
        return packet;
    }
}
