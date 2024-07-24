package habbo.rooms.entities.components.variables;

import com.google.inject.Inject;
import core.configuration.ConfigurationManager;
import habbo.rooms.entities.IRoomEntity;
import habbo.variables.IVariable;
import habbo.variables.VariableManager;
import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.NotNull;

public class EntityVariablesComponent extends VariableManager implements habbo.rooms.entities.variables.IEntityVariablesComponent {
    private @NotNull IRoomEntity entity;
    private @Inject ConfigurationManager configurationManager;

    public void init(final IRoomEntity entity) {
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
