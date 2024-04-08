package packets.outgoing.navigator;

import habbo.navigator.INavigatorManager;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

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
