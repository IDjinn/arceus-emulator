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
        if (client.getHabbo().getPlayerEntity() == null) return;

        var entity = client.getHabbo().getPlayerEntity();

        entity.setPosition(new Position(packet.readInt(), packet.readInt()));
        entity.getClient().sendMessage(new RoomUserStatusComposer(entity.getRoom().getEntitiesComponent().getEntities()));
    }
}
