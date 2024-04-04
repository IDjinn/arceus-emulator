package core.locking;

import org.jetbrains.annotations.NotNull;

public interface IConcurrentLock {
    boolean lock(@NotNull ILockValue key);

    ILockValue lock(ILockType type, @NotNull String key);

    boolean isLocked(@NotNull ILockValue key);

    boolean isLocked(@NotNull String key);

    @NotNull
    String getLockKey(ILockType type, @NotNull String index);

    boolean unlock(@NotNull ILockValue key);
}
