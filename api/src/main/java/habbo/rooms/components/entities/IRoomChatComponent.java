package habbo.rooms.components.entities;

import habbo.internationalization.LocalizedString;
import habbo.rooms.IRoomComponent;
import habbo.rooms.entities.IRoomEntity;

public interface IRoomChatComponent extends IRoomComponent {
    void talk(IRoomEntity entity, String message, int bubble);

    void shout(IRoomEntity entity, String message, int bubble);

    void whisper(IRoomEntity entity, String message, int bubble);

    void talk(IRoomEntity entity, LocalizedString message, int bubble);

    void shout(IRoomEntity entity, LocalizedString message, int bubble);

    void whisper(IRoomEntity entity, LocalizedString message, int bubble);
}
