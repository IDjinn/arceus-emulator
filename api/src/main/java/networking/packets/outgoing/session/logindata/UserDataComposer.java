package networking.packets.outgoing.session.logindata;

import habbohotel.users.IHabbo;
import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class UserDataComposer extends OutgoingPacket {
    public UserDataComposer(IHabbo habbo) {
        super(OutgoingHeaders.UserDataComposer);

        appendInt(habbo.getId());
        appendString(habbo.getName());
        appendString(habbo.getLook());
        appendString(habbo.getGender());
        appendString(habbo.getMotto());
        appendString(habbo.getName());
        appendBoolean(false);
        appendInt(0); // respectPointsReceived
        appendInt(0);//respectPointsToGive
        appendInt(0);//petRespectPointsToGive
        appendBoolean(false);
        appendString("01-01-1970 00:00:00");
        appendBoolean(true); //can change name.
        appendBoolean(false); //safatey locked

    }
}
