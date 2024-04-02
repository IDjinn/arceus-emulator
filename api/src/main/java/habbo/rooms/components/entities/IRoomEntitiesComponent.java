package habbo.rooms.components.entities;

import habbo.habbos.IHabbo;
import habbo.rooms.IRoomComponent;
import habbo.rooms.entities.IPlayerEntity;
import habbo.rooms.entities.IRoomEntity;

import java.util.List;

public interface IRoomEntitiesComponent extends IRoomComponent {
    IPlayerEntity createHabboEntity(IHabbo habbo);

    List<IRoomEntity> getEntities();

    List<IPlayerEntity> getPlayers();
}
