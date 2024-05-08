package packets.outgoing.navigator;

import habbo.habbos.data.navigator.IHabboNavigatorSearch;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

import java.util.List;

public class NewNavigatorSavedSearchesComposer extends OutgoingPacket {
    public NewNavigatorSavedSearchesComposer(final List<IHabboNavigatorSearch> searches) {
        super(OutgoingHeaders.NewNavigatorSavedSearchesComposer);

        this.appendInt(searches.size());

        for (final IHabboNavigatorSearch search : searches) {
            this.appendInt(search.getId());
            this.appendString(search.getSearchCode());
            this.appendString(search.getFilter());
            this.appendString("");
        }
    }
}
