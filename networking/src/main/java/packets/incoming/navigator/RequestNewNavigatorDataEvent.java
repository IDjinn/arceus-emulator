package packets.incoming.navigator;

import com.google.inject.Singleton;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;
import packets.outgoing.navigator.*;

@Singleton
public class RequestNewNavigatorDataEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestNewNavigatorDataEvent;
    }

    @Override
    public void Parse(IncomingPacket packet, INitroClient client) {
        client.sendMessages(
                new NewNavigatorSettingsComposer(),
                new NewNavigatorMetaDataComposer(),
                new NewNavigatorLiftedRoomsComposer(),
                new NewNavigatorCollapsedCategoriesComposer(),
                new NewNavigatorSavedSearchesComposer(),
                new NewNavigatorEventCategoriesComposer()
        );
    }
}
