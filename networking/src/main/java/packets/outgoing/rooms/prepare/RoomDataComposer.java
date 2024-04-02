package packets.outgoing.rooms.prepare;

import habbo.habbos.IHabbo;
import habbo.rooms.IRoom;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class RoomDataComposer extends OutgoingPacket {
    public RoomDataComposer(IRoom room, IHabbo habbo, boolean roomForward, boolean enterRoom) {
        super(OutgoingHeaders.RoomDataComposer);

        appendBoolean(enterRoom);
        appendInt(room.getId());
        appendString(room.getName());
        // if (room.isPublicRoom()) {
        appendInt(0);
        appendString("");
        // } else {
        //     appendInt(room.getOwnerId());
        //     appendString(room.getOwnerName());
        // }
        appendInt(room.getRoomAccess().getState());
        appendInt(0);
        appendInt(100);
        appendString("room.Data.Description");
        appendInt(2); // trade mode
        appendInt(0);
        appendInt(2); //Top rated room rank
        appendInt(1); //category

        // if (!room.getTags().isEmpty()) {
        //     String[] tags = room.getTags().split(";");
        //     appendInt(tags.length);
        //     for (String s : tags) {
        //         appendString(s);
        //     }
        // } else {
        appendInt(0);
        // }

        var bitflags = 0;

        // if (room.getGuildId() > 0) {
        //     base = base | 2;
        // }
        //
        // if (!room.isPublicRoom()) {
        //     base = base | 8;
        // }
        //
        // if (room.isPromoted()) {
        //     base = base | 4;
        // }
        //
        // if (room.isAllowPets()) {
        //     base = base | 16;
        // }

        appendInt(bitflags);

        // if (room.getGuildId() > 0) {
        //     Guild g = Emulator.getGameEnvironment().getGuildManager().getGuild(room.getGuildId());
        //     if (g != null) {
        //         appendInt(g.getId());
        //         appendString(g.getName());
        //         appendString(g.getBadge());
        //     } else {
        //         appendInt(0);
        //         appendString("");
        //         appendString("");
        //     }
        // }
        //
        // if (room.isPromoted()) {
        //     appendString(room.getPromotion().getTitle());
        //     appendString(room.getPromotion().getDescription());
        //     appendInt((room.getPromotion().getEndTimestamp() - Emulator.getIntUnixTimestamp()) / 60);
        // }

        appendBoolean(roomForward);
        appendBoolean(false); // staffpicked
        appendBoolean(false); // is group member
        appendBoolean(false); // isroommuted

        appendInt(0);
        appendInt(0);
        appendInt(0);

        appendBoolean(false); //mute all button

        appendInt(0);
        appendInt(1);
        appendInt(1);
        appendInt(50);
        appendInt(2);
    }
}
