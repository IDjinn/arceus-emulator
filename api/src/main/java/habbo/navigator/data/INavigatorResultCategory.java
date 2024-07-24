package habbo.navigator.data;

import networking.packets.OutgoingPacket;

public interface INavigatorResultCategory {
    void write(OutgoingPacket<U> packet);

    boolean filterRooms(INavigatorFilterType type, String search);
}
