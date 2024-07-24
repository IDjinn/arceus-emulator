package habbo.rooms.entities;

import com.google.inject.AbstractModule;
import habbo.rooms.entities.components.position.EntityPositionComponent;
import habbo.rooms.entities.components.status.EntityStatusComponent;
import habbo.rooms.entities.position.IEntityPositionComponent;
import habbo.rooms.entities.status.IEntityStatusComponent;

public class RoomEntityModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(IEntityPositionComponent.class).to(EntityPositionComponent.class);
        this.bind(IEntityStatusComponent.class).to(EntityStatusComponent.class);
    }
}
