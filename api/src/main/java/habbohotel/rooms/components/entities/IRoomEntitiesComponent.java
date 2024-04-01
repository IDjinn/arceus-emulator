package habbohotel.rooms.components.entities;

import habbohotel.rooms.IRoomComponent;
import habbohotel.rooms.entities.IPlayerEntity;
import habbohotel.rooms.entities.IRoomEntity;
import habbohotel.users.IHabbo;

import java.util.List;

public interface IRoomEntitiesComponent extends IRoomComponent {
    IPlayerEntity createHabboEntity(IHabbo habbo);

    List<IRoomEntity> getEntities();

    List<IPlayerEntity> getPlayers();
}
