package packets.outgoing.navigator;

import habbo.habbos.data.IHabboNavigator;
import habbo.habbos.data.navigator.IHabboNavigatorSearch;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

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

        this.appendInt(this.tabs.length);

        for (String tabName : this.tabs) {
            this.appendString(tabName);

            final List<IHabboNavigatorSearch> savedSearches = navigator.getNavigatorSearchForTab(tabName);

            this.appendInt(savedSearches.size());

            for (final IHabboNavigatorSearch search : savedSearches) {
                search.write(this);
            }
        }
    }
}
