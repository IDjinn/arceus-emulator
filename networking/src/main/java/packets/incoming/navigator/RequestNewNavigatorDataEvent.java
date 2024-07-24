package packets.incoming.navigator;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.navigator.INavigatorManager;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
import networking.packets.IncomingEvent;
import packets.incoming.IncomingHeaders;
import packets.outgoing.navigator.*;

@Singleton
public class RequestNewNavigatorDataEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestNewNavigatorDataEvent;
    }

    @Inject
    private INavigatorManager navigatorManager;

    @Override
    public void parse(IIncomingPacket packet, IClient client) {
        client.sendMessages(
                new NewNavigatorSettingsComposer(client.getHabbo().getNavigator().getNavigatorWindowSettings()),
                new NewNavigatorMetaDataComposer(client.getHabbo().getNavigator()),
                new NewNavigatorLiftedRoomsComposer(),
                new NewNavigatorCollapsedCategoriesComposer(),
                new NewNavigatorEventCategoriesComposer(this.navigatorManager)
        );
    }
}
