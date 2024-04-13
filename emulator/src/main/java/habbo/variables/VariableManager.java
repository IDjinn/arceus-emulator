package habbo.variables;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class VariableManager implements IVariableManager {
    private final Map<String, IVariable> variables;
    private boolean needUpdate;

    public VariableManager() {
        this.variables = new ConcurrentHashMap<>();
    }


    @Override
    public Map<String, IVariable> getVariables() {
        return this.variables;
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
    public boolean isNeedUpdate() {
        return this.needUpdate;
    }

    @Override
    public void update() {

    }

    @Override
    public void setNeedUpdate(final boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

//    @Override
//    public void sendUpdate() {
//        if (this.getEntity() instanceof IPlayerEntity playerEntity) {
//            var packet = new OutgoingPacket(8899);
//            packet.appendInt(this.variables.size());
//            for (var entry : this.variables.values()) {
//                packet.appendString(entry.getKey());
//                packet.appendString(entry.getValue());
//            }
//            playerEntity.getClient().sendMessage(packet);
//        }
//    }

    @Override
    public void removeVariable(@NotNull final String key) {
        this.variables.remove(key);
        this.setNeedUpdate(true);
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void tick() {
        for (final var variable : this.getVariables().values()) {
            if (variable.expiresAt().isEmpty()) continue;
            if (variable.expiresAt().get().isBefore(Instant.now())) {
                this.removeVariable(variable.getKey());
            }
        }
    }
}
