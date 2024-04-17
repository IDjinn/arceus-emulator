package habbo.rooms.entities.events;

import core.events.IEvent;
import habbo.rooms.entities.IRoomEntity;

import java.sql.Timestamp;

public record RoomEntityTalkEvent(IRoomEntity entity, String message, Timestamp timestamp) implements IEvent {
}
