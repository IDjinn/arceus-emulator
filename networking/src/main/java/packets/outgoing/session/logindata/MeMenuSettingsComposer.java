package packets.outgoing.session.logindata;

import habbo.habbos.IHabbo;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class MeMenuSettingsComposer extends OutgoingPacket { // TODO: USER CONFIGURATION SETTINGS
    public MeMenuSettingsComposer(IHabbo habbo) {
        super(OutgoingHeaders.MeMenuSettingsComposer);
        this.appendInt(100);
        this.appendInt(100);
        this.appendInt(100);
        this.appendBoolean(false);
        this.appendBoolean(false);
        this.appendBoolean(false);
        this.appendInt(0);
        this.appendInt(0);
    }
}
