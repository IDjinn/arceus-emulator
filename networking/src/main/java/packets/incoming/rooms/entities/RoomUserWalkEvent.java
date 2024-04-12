package packets.incoming.rooms.entities;

import networking.client.IClient;
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
    public void parse(IIncomingPacket packet, IClient client) {
        if (client.getHabbo().getPlayerEntity() == null) return;

        var player = client.getHabbo().getPlayerEntity();
        player.getWalkPath().clear();
        player.setGoal(new Position(packet.readInt(), packet.readInt()));
    }
}
