package packets.outgoing.rooms.prepare;

import habbo.habbos.IHabbo;
import habbo.rooms.IRoom;
import habbo.rooms.writers.RoomWriter;
import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class RoomDataComposer extends OutgoingPacket {
    public RoomDataComposer(IRoom room, IHabbo habbo, boolean roomForward, boolean enterRoom) {
        super(OutgoingHeaders.RoomDataComposer);

        RoomWriter.write(room, this);

        appendBoolean(roomForward);
        appendBoolean(room.getData().isStaffPicked());
        appendBoolean(false); // TODO: Check if habbo is member of room guild
        appendBoolean(false); // TODO: is muted

        appendInt(room.getData().getWhoCanMute());
        appendInt(room.getData().getWhoCanKick());
        appendInt(room.getData().getWhoCanBan());

        appendBoolean(false); // TODO: Permissions: mute all button

        appendInt(room.getData().getChatMode());
        appendInt(room.getData().getChatWeight());
        appendInt(room.getData().getChatSpeed());
        appendInt(room.getData().getChatDistance());
        appendInt(room.getData().getChatProtection());
    }
}
