package habbo.rooms.components.entities;

import com.google.inject.Inject;
import habbo.internationalization.InternationalizationManager;
import habbo.internationalization.LocalizedString;
import habbo.rooms.IRoom;
import habbo.rooms.entities.IRoomEntity;
import habbo.rooms.entities.events.RoomEntityTalkEvent;
import packets.outgoing.rooms.entities.chat.RoomUserShoutMessageComposer;
import packets.outgoing.rooms.entities.chat.RoomUserTalkMessageComposer;
import packets.outgoing.rooms.entities.chat.RoomUserWhisperMessageComposer;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Locale;

public class RoomChatComponent implements IRoomChatComponent {
    private IRoom room;
    private @Inject InternationalizationManager internationalizationManager;

    @Override
    public void talk(final IRoomEntity entity, final String message, final int bubble) { // TODO MOVE THIS TO ENTITY*
        final var emotion = 0;
        final var event = this.getRoom().getEventHandler().onEvent(new RoomEntityTalkEvent(
                entity,
                message,
                emotion,
                bubble,
                Timestamp.from(Instant.now()))
        );

        if (event.isCancelled()) return;

        this.getRoom().broadcastMessage(new RoomUserTalkMessageComposer(
                entity,
                event.message(),
                event.emotion(),
                event.bubble()
        ));
    }

    @Override
    public void shout(final IRoomEntity entity, final String message, final int bubble) {
        this.getRoom().broadcastMessage(new RoomUserShoutMessageComposer(entity, message, 0, bubble));
    }

    @Override
    public void whisper(final IRoomEntity entity, final String message, final int bubble) {
        this.getRoom().broadcastMessage(new RoomUserWhisperMessageComposer(entity, message, 0, bubble));
    }

    @Override
    public void talk(final IRoomEntity entity, final LocalizedString message, final int bubble) { // TODO: THIS USE 
        // CLIENT LOCALE INSTEAD HARD-CODED ENGLISH
        this.getRoom().broadcastMessage(new RoomUserTalkMessageComposer(entity, this.internationalizationManager.getLocalizedString(message,
                Locale.ENGLISH), 0, bubble));
    }

    @Override
    public void shout(final IRoomEntity entity, final LocalizedString message, final int bubble) {
        this.getRoom().broadcastMessage(new RoomUserShoutMessageComposer(entity, this.internationalizationManager.getLocalizedString(message, Locale.ENGLISH), 0, bubble));
    }

    @Override
    public void whisper(final IRoomEntity entity, final LocalizedString message, final int bubble) {
        this.getRoom().broadcastMessage(new RoomUserWhisperMessageComposer(entity, this.internationalizationManager.getLocalizedString(message, Locale.ENGLISH), 0, bubble));
    }

    @Override
    public IRoom getRoom() {
        return this.room;
    }

    @Override
    public void init(final IRoom room) {
        this.room = room;
    }

    @Override
    public void destroy() {

    }
}
