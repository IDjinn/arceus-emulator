package habbo.navigator.data;

import networking.packets.IOutgoingPacket;

public interface INavigatorResultCategory {
    void write(IOutgoingPacket packet);

    boolean filterRooms(INavigatorFilterType type, String search);
}
