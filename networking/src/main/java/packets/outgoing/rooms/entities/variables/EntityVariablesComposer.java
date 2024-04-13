package packets.outgoing.rooms.entities.variables;

import habbo.variables.IVariable;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

import java.util.Map;

public class EntityVariablesComposer extends OutgoingPacket {
    public EntityVariablesComposer(Map<String, IVariable> variables) {
        super(OutgoingHeaders.EntityVariablesComposer);

        final var visible = variables.values().stream().filter(IVariable::isVisible).toList();
        this.appendInt(visible.size());
        for (final var variable : visible) {
            variable.serialize(this);
        }
    }
}
