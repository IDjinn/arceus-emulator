package packets.outgoing.navigator.search;

import habbo.navigator.data.INavigatorResultCategory;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

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
