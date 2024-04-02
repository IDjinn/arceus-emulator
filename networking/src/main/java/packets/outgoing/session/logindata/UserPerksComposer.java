package packets.outgoing.session.logindata;

import habbo.habbos.IHabbo;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class UserPerksComposer extends OutgoingPacket {
    public UserPerksComposer(IHabbo habbo) {
        super(OutgoingHeaders.UserPerksComposer);
        appendInt(15);

        appendString("USE_GUIDE_TOOL");
        appendString("requirement.unfulfilled.helper_level_4");
        appendBoolean(false);

        appendString("GIVE_GUIDE_TOURS");
        appendString("");
        appendBoolean(false);

        appendString("JUDGE_CHAT_REVIEWS");
        appendString("requirement.unfulfilled.helper_level_6");
        appendBoolean(false);

        appendString("VOTE_IN_COMPETITIONS");
        appendString("requirement.unfulfilled.helper_level_2");
        appendBoolean(true);

        appendString("CALL_ON_HELPERS");
        appendString("");
        appendBoolean(true);

        appendString("CITIZEN");
        appendString("");
        appendBoolean(true);

        appendString("TRADE");
        appendString("requirement.unfulfilled.no_trade_lock");
        appendBoolean(true);

        appendString("HEIGHTMAP_EDITOR_BETA");
        appendString("requirement.unfulfilled.feature_disabled");
        appendBoolean(true);

        appendString("BUILDER_AT_WORK");
        appendString("");
        appendBoolean(true);

        appendString("CALL_ON_HELPERS");
        appendString("");
        appendBoolean(true);

        appendString("CAMERA");
        appendString("");
        appendBoolean(true);

        appendString("NAVIGATOR_PHASE_TWO_2014");
        appendString("");
        appendBoolean(true);

        appendString("MOUSE_ZOOM");
        appendString("");
        appendBoolean(true);

        appendString("NAVIGATOR_ROOM_THUMBNAIL_CAMERA");
        appendString("");
        appendBoolean(true);

        appendString("HABBO_CLUB_OFFER_BETA");
        appendString("");
        appendBoolean(true);

    }
}
