package packets.incoming.navigator;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.navigator.INavigatorManager;
import networking.client.INitroClient;
import networking.packets.IIncomingPacket;
import packets.incoming.IncomingEvent;
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
    public void parse(IIncomingPacket packet, INitroClient client) {
        client.sendMessages(
                new NewNavigatorSettingsComposer(client.getHabbo().getNavigator().getNavigatorWindowSettings()),
                new NewNavigatorMetaDataComposer(client.getHabbo().getNavigator()),
                new NewNavigatorLiftedRoomsComposer(),
                new NewNavigatorCollapsedCategoriesComposer(),
                new NewNavigatorEventCategoriesComposer(this.navigatorManager)
        );
    }
}
