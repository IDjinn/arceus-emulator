package networking.packets.outgoing.navigator.search;

import habbohotel.rooms.IRoom;
import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

import java.util.SequencedCollection;

public class NewNavigatorSearchResultsComposer extends OutgoingPacket {
    public NewNavigatorSearchResultsComposer(String code, String query, SequencedCollection<IRoom> rooms) {
        super(OutgoingHeaders.NewNavigatorSearchResultsComposer);

        appendString(code);
        appendString(query);

        appendInt(rooms.size());
        for (IRoom room : rooms) {
            room.serialize(this);
        }
    }
}
