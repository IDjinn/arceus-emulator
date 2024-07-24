package packets.outgoing.rooms.entities.chat;

import com.google.common.collect.Sets;
import habbo.rooms.entities.IRoomEntity;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

import java.util.Set;

public class RoomUserWhisperMessageComposer extends OutgoingPacket {
    public RoomUserWhisperMessageComposer(final IRoomEntity entity,
                                          final String message) {
        this(entity, message, 0, 0, Sets.newHashSet());
    }

    public RoomUserWhisperMessageComposer(final IRoomEntity entity,
                                          final String message,
                                          final int emotion,
                                          final int bubble) {
        this(entity, message, emotion, bubble, Sets.newHashSet());
    }

    public RoomUserWhisperMessageComposer(final IRoomEntity entity,
                                          final String message,
                                          final int emotion,
                                          final int bubble,
                                          final Set<String> urls) {
        super(OutgoingHeaders.RoomUserWhisperComposer);

        this.appendInt(entity.getVirtualId());
        this.appendString(message);
        this.appendInt(emotion);
        this.appendInt(bubble);
        this.appendInt(urls.size());
        for (var url : urls) {
            this.appendString(url);
        }
        this.appendInt(message.length());
    }
}
