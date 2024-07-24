package packets.outgoing.navigator;

import habbo.navigator.INavigatorManager;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class NewNavigatorEventCategoriesComposer extends OutgoingPacket {
    public NewNavigatorEventCategoriesComposer(final INavigatorManager navigatorManager) {
        super(OutgoingHeaders.NewNavigatorEventCategoriesComposer);

        this.appendInt(navigatorManager.getEventCategories().size());

        navigatorManager.getEventCategories().forEach((_, category) -> {
            this.appendInt(category.getId());
            this.appendString(category.getName());
            this.appendBoolean(category.isVisible());
        });
    }
}
