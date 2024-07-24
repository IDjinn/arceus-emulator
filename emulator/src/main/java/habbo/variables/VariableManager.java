package habbo.variables;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class VariableManager implements IVariableManager {
    private final VariableMessageFactory messageFormatter;
    private final Map<String, IVariable<?>> variables;
    private boolean needUpdate;

    @Inject
    public VariableManager() {
        this.variables = new ConcurrentHashMap<>();
        this.messageFormatter = new VariableMessageFactory();
    }

    @Override
    public Map<String, IVariable<?>> getVariables() {
        return this.variables;
    }

    @Override
    public <T> IVariable<T> getOrCreate(final IVariable<T> variable) {
        if (this.variables.containsKey(variable.getKey()))
            return (IVariable<T>) this.variables.get(variable.getKey());

        this.set(variable);
        return variable;
    }

    @Override
    public <T> IVariable<T> setOrCreate(final IVariable<T> variable) {
        if (this.variables.containsKey(variable.getKey())) {
            final var foundVariable = (IVariable<T>) this.variables.get(variable.getKey());
            final var oldValue = foundVariable.getValue();
            foundVariable.setValue(variable.getValue());
            this.setNeedUpdate(!Objects.equals(oldValue, variable.getValue()));
            return foundVariable;
        }
        return this.set(variable);
    }

    @Override
    public <T> IVariable<T> setOrCreate(final String key, final T value) {
        if (this.variables.containsKey(key)) {
            final var foundVariable = (IVariable<T>) this.variables.get(key);
            final var oldValue = foundVariable.getValue();
            foundVariable.setValue(value);
            this.setNeedUpdate(!Objects.equals(oldValue, value));
            return foundVariable;
        }
        return this.set(new Variable<>(
                key,
                value
        ));
    }

    @Override
    public @Nullable <T> IVariable<T> get(@NotNull final String key) {
        return (IVariable<T>) this.variables.get(key);
    }

    @Override
    public @Nullable <T> IVariable<T> get(final @NotNull String key, final Class<T> valueType) {
        return (IVariable<T>) this.variables.get(key);
    }

    @Override
    public <T> IVariable<T> set(final IVariable<T> variable) {
        this.variables.put(variable.getKey(), variable);
        this.setNeedUpdate(true);
        return variable;
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
    public void deleteVariable(@NotNull final String key) {
        this.variables.remove(key);
        this.setNeedUpdate(true);
    }

    @Override
    public void tick() {
        for (final var variable : this.getVariables().values()) {
            if (variable.expiresAt().isEmpty()) {
            }
//   TODO          if (variable.expiresAt().get().isBefore(Instant.now())) {
//                this.removeVariable(variable.getKey());
//            }
        }
    }

    @Override
    public IVariableMessageFactory getFormatter() {
        return this.messageFormatter;
    }
}
