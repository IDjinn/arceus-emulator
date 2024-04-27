package packets.outgoing.navigator;

import habbo.navigator.INavigatorManager;
import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class NewNavigatorEventCategoriesComposer extends OutgoingPacket {
    public NewNavigatorEventCategoriesComposer(final INavigatorManager navigatorManager) {
        super(OutgoingHeaders.NewNavigatorEventCategoriesComposer);

        appendInt(navigatorManager.getEventCategories().size());

        navigatorManager.getEventCategories().forEach((_, category) -> {
            appendInt(category.getId());
            appendString(category.getName());
            appendBoolean(category.isVisible());
        });
    }
}
