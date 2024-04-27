package packets.incoming.rooms.entities;

import habbo.GameErrors;
import habbo.rooms.components.gamemap.IRoomTile;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;
import utils.pathfinder.Position;
import utils.result.GameError;
import utils.result.Result;

public class RoomUserWalkEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RoomUserWalkEvent;
    }

    @Override
    public Result<Boolean, GameError> validate(final IIncomingPacket packet, final IClient client) {
        if (client.getHabbo().getPlayerEntity() == null || client.getHabbo().getRoom() == null)
            return Result.error(GameErrors.Packets.Generic.MUST_BE_IN_ROOM);

        if (!client.getHabbo().getPlayerEntity().canWalk())
            return Result.error(GameErrors.Packets.Room.Entities.CANNOT_WALK);

        final var gameMap = client.getHabbo().getRoom().getGameMap();
        return packet.assertion()
                .assertInteger(value -> value >= 0 && value < gameMap.getMaxX(), "x coordinate", GameErrors.Packets.Room.Entities.INVALID_WALK)
                .assertInteger(value -> value >= 0 && value < gameMap.getMaxY(), "y coordinate", GameErrors.Packets.Room.Entities.INVALID_WALK)
                .result();
    }

    @Override
    public void parse(IIncomingPacket packet, IClient client) {
        if (client.getHabbo().getPlayerEntity() == null || client.getHabbo().getRoom() == null) return;

        final Position goal = new Position(packet.readInt(), packet.readInt());
        final IRoomTile tileGoal = client.getHabbo().getRoom().getGameMap().getTile(goal);
        
        var player = client.getHabbo().getPlayerEntity();
        player.getWalkPath().clear();
        player.setGoal(goal);
    }
}
