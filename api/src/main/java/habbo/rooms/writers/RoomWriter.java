package habbo.rooms.writers;

import com.google.inject.Singleton;
import habbo.rooms.IRoom;
import networking.packets.IOutgoingPacket;

@Singleton
public abstract class RoomWriter { // TODO: Maybe make this an interface? Or add in the injector?
    public static void write(
            final IRoom room,
            final IOutgoingPacket<U> packet
    ) {
        packet.appendInt(room.getData().getId());
        packet.appendString(room.getData().getName());

        packet.appendInt(room.getData().isPublic() ? 0 : room.getData().getOwnerId());
        packet.appendString(room.getData().isPublic() ? "" : room.getData().getOwnerName());

        packet.appendInt(room.getData().getAccessState().getState());
        packet.appendInt(0);
        packet.appendInt(room.getData().getMaxUsers());
        packet.appendString(room.getData().getDescription());
        packet.appendInt(room.getData().getTradeMode());
        packet.appendInt(room.getData().getScore());
        packet.appendInt(0);
        packet.appendInt(room.getData().getCategoryId()); // this.category

        packet.appendInt(room.getData().getTags().size());

        room.getData().getTags().forEach(packet::appendString);

        int base = 0;

        if (room.getData().getGuildId() > 0) base |= 2;
        if (room.getData().isPromoted()) base |= 4;
        if (!room.getData().isPublic()) base |= 8;
        if (!room.getData().allowPets()) base |= 16;

        packet.appendInt(base);

        // TODO: Write room guild

        // TODO: Write room promotion
    }
}
