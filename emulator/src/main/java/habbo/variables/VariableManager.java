package habbo.variables;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class VariableManager implements IVariableManager {
    private final VariableMessageFactory messageFormatter;
    private final Map<String, IVariable> variables;
    private boolean needUpdate;

    @Inject
    public VariableManager() {
        this.variables = new ConcurrentHashMap<>();
        this.messageFormatter = new VariableMessageFactory();
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
    public void setNeedUpdate(final boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    @Override
    public void removeVariable(@NotNull final String key) {
        this.variables.remove(key);
        this.setNeedUpdate(true);
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

    @Override
    public IVariableMessageFactory getFormatter() {
        return this.messageFormatter;
    }
}
