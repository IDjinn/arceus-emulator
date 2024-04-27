package packets.outgoing.session.logindata;

import habbo.habbos.IHabbo;
import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

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
