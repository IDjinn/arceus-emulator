package packets.incoming.guest;

import com.google.inject.Singleton;
import io.netty.channel.ChannelHandlerContext;
import networking.packets.IIncomingPacket;
import networking.util.GameNetowrkingAttributes;
import networking.util.MachineId;
import networking.util.NoAuth;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;

@Singleton
@NoAuth
public class MachineIdEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.MachineIDEvent;
    }

    @Override
    public void parseForGuest(IIncomingPacket packet, ChannelHandlerContext ctx) {
        var _ = packet.readString();
        var machineId = packet.readString();

        ctx.attr(GameNetowrkingAttributes.MACHINE_ID).set(new MachineId(machineId));
    }
}
