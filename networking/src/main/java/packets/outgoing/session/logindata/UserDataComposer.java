package packets.outgoing.session.logindata;

import habbo.habbos.IHabbo;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class UserDataComposer extends OutgoingPacket {
    public UserDataComposer(IHabbo habbo) {
        super(OutgoingHeaders.UserDataComposer);

        this.appendInt(habbo.getData().getId());
        this.appendString(habbo.getData().getUsername());
        this.appendString(habbo.getData().getLook());
        this.appendString(habbo.getData().getGender());
        this.appendString(habbo.getData().getMotto());
        this.appendString(habbo.getData().getUsername());
        this.appendBoolean(false);
        this.appendInt(habbo.getSettings().getRespectPointsReceived());
        this.appendInt(habbo.getSettings().getRespectPointsGiven());
        this.appendInt(habbo.getSettings().getPetRespectPointsToGive());
        this.appendBoolean(false);
        this.appendString("01-01-1970 00:00:00");
        this.appendBoolean(habbo.getSettings().allowNameChange());
        this.appendBoolean(false);

    }
}
