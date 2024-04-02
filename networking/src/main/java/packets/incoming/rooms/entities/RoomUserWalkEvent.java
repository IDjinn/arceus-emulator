package packets.incoming.rooms.entities;

import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;
import packets.outgoing.rooms.RoomUserStatusComposer;
import utils.Position;
 
public class RoomUserWalkEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RoomUserWalkEvent;
    }

    @Override
    public void Parse(IncomingPacket packet, INitroClient client) {
        if (!client.getHabbo().isInRoom())
            return;

        var player = client.getHabbo().getPlayerEntity();
        player.setPosition(new Position(packet.readInt(), packet.readInt()));
        player.getClient().sendMessage(new RoomUserStatusComposer(player.getRoom().getEntitiesComponent().getEntities()));
    }
}
