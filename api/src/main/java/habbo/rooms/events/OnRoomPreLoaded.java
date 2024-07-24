package habbo.rooms.events;

import core.events.Event;
import habbo.rooms.IRoom;

public record OnRoomPreLoaded(IRoom room) implements Event {
}
