package packets.outgoing.session.logindata;

import habbo.habbos.IHabbo;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class UserPerksComposer extends OutgoingPacket {
    public UserPerksComposer(IHabbo habbo) {
        super(OutgoingHeaders.UserPerksComposer);
        this.appendInt(15);

        this.appendString("USE_GUIDE_TOOL");
        this.appendString("requirement.unfulfilled.helper_level_4");
        this.appendBoolean(false);

        this.appendString("GIVE_GUIDE_TOURS");
        this.appendString("");
        this.appendBoolean(false);

        this.appendString("JUDGE_CHAT_REVIEWS");
        this.appendString("requirement.unfulfilled.helper_level_6");
        this.appendBoolean(false);

        this.appendString("VOTE_IN_COMPETITIONS");
        this.appendString("requirement.unfulfilled.helper_level_2");
        this.appendBoolean(true);

        this.appendString("CALL_ON_HELPERS");
        this.appendString("");
        this.appendBoolean(true);

        this.appendString("CITIZEN");
        this.appendString("");
        this.appendBoolean(true);

        this.appendString("TRADE");
        this.appendString("requirement.unfulfilled.no_trade_lock");
        this.appendBoolean(true);

        this.appendString("HEIGHTMAP_EDITOR_BETA");
        this.appendString("requirement.unfulfilled.feature_disabled");
        this.appendBoolean(true);

        this.appendString("BUILDER_AT_WORK");
        this.appendString("");
        this.appendBoolean(true);

        this.appendString("CALL_ON_HELPERS");
        this.appendString("");
        this.appendBoolean(true);

        this.appendString("CAMERA");
        this.appendString("");
        this.appendBoolean(true);

        this.appendString("NAVIGATOR_PHASE_TWO_2014");
        this.appendString("");
        this.appendBoolean(true);

        this.appendString("MOUSE_ZOOM");
        this.appendString("");
        this.appendBoolean(true);

        this.appendString("NAVIGATOR_ROOM_THUMBNAIL_CAMERA");
        this.appendString("");
        this.appendBoolean(true);

        this.appendString("HABBO_CLUB_OFFER_BETA");
        this.appendString("");
        this.appendBoolean(true);

    }
}
