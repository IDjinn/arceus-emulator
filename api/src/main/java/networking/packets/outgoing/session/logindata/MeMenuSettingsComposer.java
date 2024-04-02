package networking.packets.outgoing.session.logindata;

import habbohotel.users.IHabbo;
import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class MeMenuSettingsComposer extends OutgoingPacket { // TODO: USER CONFIGURATION SETTINGS
    public MeMenuSettingsComposer(IHabbo habbo) {
        super(OutgoingHeaders.MeMenuSettingsComposer);
        appendInt(100);
        appendInt(100);
        appendInt(100);
        appendBoolean(false);
        appendBoolean(false);
        appendBoolean(false);
        appendInt(0);
        appendInt(0);
    }
}
