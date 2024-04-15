package habbo.rooms.events;

import core.events.IEvent;
import habbo.rooms.IRoom;

public record OnRoomLoaded(IRoom room) implements IEvent {
}
