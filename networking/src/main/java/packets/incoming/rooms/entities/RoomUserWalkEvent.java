package packets.incoming.rooms.entities;

import habbo.rooms.components.gamemap.IRoomTile;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
import networking.packets.IncomingEvent;
import packets.incoming.IncomingHeaders;
import utils.pathfinder.Position;
 
public class RoomUserWalkEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RoomUserWalkEvent;
    }

    @Override
    public void parse(IIncomingPacket packet, IClient client) {
        if (client.getHabbo().getEntity() == null) return;

        final Position goal = new Position(packet.readInt(), packet.readInt());
        final IRoomTile tileGoal = client.getHabbo().getRoom().getGameMap().getTile(goal);
        
        var player = client.getHabbo().getEntity();
        player.getPositionComponent().getWalkPath().clear();
        player.getPositionComponent().setGoal(goal);
    }
}
