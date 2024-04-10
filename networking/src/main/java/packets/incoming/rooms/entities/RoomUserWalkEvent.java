package packets.incoming.rooms.entities;

import networking.client.INitroClient;
import networking.packets.IIncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;
import utils.pathfinder.Position;
 
public class RoomUserWalkEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RoomUserWalkEvent;
    }

    @Override
    public void parse(IIncomingPacket packet, INitroClient client) {
        if (client.getHabbo().getPlayerEntity() == null) return;

        var player = client.getHabbo().getPlayerEntity();
        player.setGoal(new Position(packet.readInt(), packet.readInt()));
    }
}
