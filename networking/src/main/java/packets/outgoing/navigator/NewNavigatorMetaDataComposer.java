package packets.outgoing.navigator;

import habbo.habbos.data.IHabboNavigator;
import habbo.habbos.data.navigator.IHabboNavigatorSearch;
import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

import java.util.List;

public class NewNavigatorMetaDataComposer extends OutgoingPacket {
    private final String[] tabs = {
            "official_view",
            "hotel_view",
            "roomads_view",
            "myworld_view"
    };

    public NewNavigatorMetaDataComposer(final IHabboNavigator navigator) {
        super(OutgoingHeaders.NewNavigatorMetaDataComposer);

        appendInt(this.tabs.length);

        for (String tabName : this.tabs) {
            appendString(tabName);

            final List<IHabboNavigatorSearch> savedSearches = navigator.getNavigatorSearchForTab(tabName);

            appendInt(savedSearches.size());

            for (final IHabboNavigatorSearch search : savedSearches) {
                search.write(this);
            }
        }
    }
}
