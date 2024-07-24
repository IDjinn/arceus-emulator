package habbo.navigator.data;

import networking.packets.IOutgoingPacket;

public interface INavigatorResultCategory {
    void write(IOutgoingPacket<U> packet);

    boolean filterRooms(INavigatorFilterType type, String search);
}
