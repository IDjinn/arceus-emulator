package habbo.rooms.entities.events;

import core.events.IEvent;
import habbo.rooms.entities.IRoomEntity;

public record RoomEntityTalkEvent(IRoomEntity entity, String message) implements IEvent {
}
