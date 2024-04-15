package habbo.rooms.entities.events;

import core.events.IEvent;
import habbo.rooms.entities.IRoomEntity;
import utils.pathfinder.Position;

public record RoomEntityWalkEvent(IRoomEntity entity, Position oldPosition, Position newPosition) implements IEvent {
}
