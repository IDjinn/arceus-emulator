package habbo.rooms.events;

import core.events.Event;
import habbo.rooms.IRoom;

public record OnRoomLoaded(IRoom room) implements Event {
}
