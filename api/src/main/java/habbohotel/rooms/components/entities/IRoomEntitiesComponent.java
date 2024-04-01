package habbohotel.rooms.components.entities;

import habbohotel.rooms.IRoomComponent;
import habbohotel.rooms.entities.IPlayerEntity;
import habbohotel.users.IHabbo;

public interface IRoomEntitiesComponent extends IRoomComponent {
    IPlayerEntity createHabboEntity(IHabbo habbo);
}
