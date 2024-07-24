package habbo.habbos.writers;

import habbo.habbos.data.navigator.IHabboNavigatorSearch;
import networking.packets.IOutgoingPacket;

public abstract class HabboNavigatorSearchWriter {
    public static void write(
            IHabboNavigatorSearch search,
            IOutgoingPacket<U> packet
    ) {
        packet.appendInt(search.getId());
        packet.appendString(search.getSearchCode());
        packet.appendString(search.getFilter());
        packet.appendString("");
    }
}
