package core.locking;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record LockValue(@NotNull String key) implements ILockValue {
    public static LockValue fromType(ILockType type, @Nullable String key) {
        return new LockValue(STR."\{type.getLockCode()}}-\{key}");
    }
}
