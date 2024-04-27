package packets.outgoing.rooms.entities.chat;

import com.google.common.collect.Sets;
import habbo.rooms.entities.IRoomEntity;
import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

import java.util.Set;

public class RoomUserTalkMessageComposer extends OutgoingPacket {
    public RoomUserTalkMessageComposer(final IRoomEntity entity,
                                       final String message) {
        this(entity, message, 0, 0, Sets.newHashSet());
    }

    public RoomUserTalkMessageComposer(final IRoomEntity entity,
                                       final String message,
                                       final int emotion,
                                       final int bubble) {
        this(entity, message, emotion, bubble, Sets.newHashSet());
    }

    public RoomUserTalkMessageComposer(final IRoomEntity entity,
                                       final String message,
                                       final int emotion,
                                       final int bubble,
                                       final Set<String> urls) {
        super(OutgoingHeaders.RoomUserTalkComposer);

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
