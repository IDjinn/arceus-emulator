package habbo.rooms.entities.components.variables;

import com.google.inject.Inject;
import core.configuration.ConfigurationManager;
import habbo.rooms.entities.IRoomEntity;
import habbo.rooms.entities.variables.IEntityVariableManager;
import habbo.variables.IVariable;
import habbo.variables.VariableManager;
import networking.packets.OutgoingPacket;

public class EntityVariablesManager extends VariableManager implements IEntityVariableManager {
    private final IRoomEntity entity;
    private @Inject ConfigurationManager configurationManager;

    public EntityVariablesManager(IRoomEntity entity) {
        this.entity = entity;
    }

    @Override
    public IRoomEntity getEntity() {
        return this.entity;
    }

    @Override
    public void serialize(final OutgoingPacket packet) {
        if (!this.configurationManager.getBool("variables.entities.enabled")) {
            packet.appendInt(0);
            return;
        }

        final var visibleVariables = this.getVariables().values().stream().filter(IVariable::isVisible).toList();
        packet.appendInt(visibleVariables.size());
        for (final var variable : visibleVariables) {
            variable.serialize(packet);
        }
    }
}
