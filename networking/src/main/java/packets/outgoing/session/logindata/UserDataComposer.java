package packets.outgoing.session.logindata;

import habbo.habbos.IHabbo;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class UserDataComposer extends OutgoingPacket {
    public UserDataComposer(IHabbo habbo) {
        super(OutgoingHeaders.UserDataComposer);

        appendInt(habbo.getData().getId());
        appendString(habbo.getData().getUsername());
        appendString(habbo.getData().getLook());
        appendString(habbo.getData().getGender());
        appendString(habbo.getData().getMotto());
        appendString(habbo.getData().getUsername());
        appendBoolean(false);
        appendInt(habbo.getSettings().getRespectPointsReceived());
        appendInt(habbo.getSettings().getRespectPointsGiven());
        appendInt(habbo.getSettings().getPetRespectPointsToGive());
        appendBoolean(false);
        appendString("01-01-1970 00:00:00");
        appendBoolean(habbo.getSettings().allowNameChange());
        appendBoolean(false);

    }
}
