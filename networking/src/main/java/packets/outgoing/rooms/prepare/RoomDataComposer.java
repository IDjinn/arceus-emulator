package packets.outgoing.rooms.prepare;

import habbo.habbos.IHabbo;
import habbo.rooms.IRoom;
import habbo.rooms.writers.RoomWriter;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class RoomDataComposer extends OutgoingPacket {
    public RoomDataComposer(IRoom room, IHabbo habbo, boolean roomForward, boolean enterRoom) {
        super(OutgoingHeaders.RoomDataComposer);

        RoomWriter.write(room, this);

        this.appendBoolean(roomForward);
        this.appendBoolean(room.getData().isStaffPicked());
        this.appendBoolean(false); // TODO: Check if habbo is member of room guild
        this.appendBoolean(false); // TODO: is muted

        this.appendInt(room.getData().getWhoCanMute());
        this.appendInt(room.getData().getWhoCanKick());
        this.appendInt(room.getData().getWhoCanBan());

        this.appendBoolean(false); // TODO: Permissions: mute all button

        this.appendInt(room.getData().getChatMode());
        this.appendInt(room.getData().getChatWeight());
        this.appendInt(room.getData().getChatSpeed());
        this.appendInt(room.getData().getChatDistance());
        this.appendInt(room.getData().getChatProtection());
    }
}
