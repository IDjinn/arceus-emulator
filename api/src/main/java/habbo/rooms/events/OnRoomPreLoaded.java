package habbo.rooms.events;

import core.events.IEvent;
import habbo.rooms.IRoom;

public record OnRoomPreLoaded(IRoom room) implements IEvent {
}
