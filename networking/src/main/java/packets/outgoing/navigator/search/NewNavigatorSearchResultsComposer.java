package packets.outgoing.navigator.search;

import habbo.navigator.data.INavigatorResultCategory;
import habbo.rooms.IRoom;
import habbo.rooms.enums.RoomAccessState;
import networking.packets.OutgoingPacket;
import networking.util.ISerializable;
import packets.outgoing.OutgoingHeaders;

import java.util.ArrayList;
import java.util.List;

public class NewNavigatorSearchResultsComposer extends OutgoingPacket {

    public NewNavigatorSearchResultsComposer(String code, String query, List<INavigatorResultCategory> categories) {
        super(OutgoingHeaders.NewNavigatorSearchResultsComposer);

        appendString(code);
        appendString(query);

        appendInt(categories.size());

        for (final INavigatorResultCategory category : categories) {
            category.write(this);
        }
    }
}
