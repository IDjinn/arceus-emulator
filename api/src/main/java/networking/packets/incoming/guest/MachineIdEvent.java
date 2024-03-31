package networking.packets.incoming.guest;

import com.google.inject.Singleton;
import io.netty.channel.ChannelHandlerContext;
import networking.packets.IncomingPacket;
import networking.packets.incoming.IncomingEvent;
import networking.packets.incoming.IncomingHeaders;
import networking.util.GameServerAttributes;
import networking.util.MachineId;
import networking.util.NoAuth;

@Singleton
@NoAuth
public class MachineIdEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.MachineIDEvent;
    }

    @Override
    public void ParseForGuest(IncomingPacket packet, ChannelHandlerContext ctx) {
        var _ = packet.readString();
        var machineId = packet.readString();

        ctx.attr(GameServerAttributes.MACHINE_ID).set(new MachineId(machineId));
    }
}
