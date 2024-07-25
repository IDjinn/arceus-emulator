package habbo.navigator.data;

import networking.packets.IOutgoingPacket;

public interface INavigatorResultCategory {
    boolean filterRooms(INavigatorFilterType type, String search);
}
