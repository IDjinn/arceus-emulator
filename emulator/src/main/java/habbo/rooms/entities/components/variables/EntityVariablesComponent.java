package habbo.rooms.entities.components.variables;

import habbo.rooms.entities.IPlayerEntity;
import habbo.rooms.entities.IRoomEntity;
import habbo.rooms.entities.variables.IEntityVariablesComponent;
import habbo.rooms.entities.variables.IVariable;
import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class EntityVariablesComponent implements IEntityVariablesComponent {
    private final Map<String, IVariable> variables;
    private final IRoomEntity entity;
    private boolean needUpdate;

    public EntityVariablesComponent(IRoomEntity entity) {
        this.entity = entity;
        this.variables = new ConcurrentHashMap<>();
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void setOrCreate(final IVariable variable) {
        var oldValue = this.variables.put(variable.getKey(), variable);
        this.setNeedUpdate(oldValue == null || !Objects.equals(oldValue.getValue(), variable.getValue()));
    }

    @Override
    public void setOrCreate(final String variable, @Nullable final String value) {
        var var = this.get(variable);
        if (var != null) {
            this.setNeedUpdate(!Objects.equals(var.getValue(), value));
            var.setValue(value);
        } else {
            this.setOrCreate(new Variable(variable, value));
            this.setNeedUpdate(true);
        }
    }

    @Override
    public @Nullable IVariable get(@NotNull final String key) {
        return this.variables.get(key);
    }

    @Override
    public IRoomEntity getEntity() {
        return this.entity;
    }

    @Override
    public boolean isNeedUpdate() {
        return this.needUpdate;
    }

    @Override
    public void setNeedUpdate(final boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    @Override
    public void update() {
        if (this.getEntity() instanceof IPlayerEntity playerEntity) {
            var packet = new OutgoingPacket(8899);
            packet.appendInt(this.variables.size());
            for (var entry : this.variables.values()) {
                packet.appendString(entry.getKey());
                packet.appendString(entry.getValue());
            }
            playerEntity.getClient().sendMessage(packet);
        }
    }
}
