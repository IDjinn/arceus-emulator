package core.locking;

import com.google.inject.Singleton;
import org.jetbrains.annotations.NotNull;
import utils.StringBuilderHelper;

import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class ConcurrentLock implements IConcurrentLock {
    private final ConcurrentHashMap<String, Object> lock = new ConcurrentHashMap<>();

    @Override
    public boolean lock(@NotNull ILockValue lockerKey) {
        return lock.putIfAbsent(lockerKey.key(), new Object()) == null;
    }

    @Override
    public ILockValue lock(ILockType type, @NotNull String key) {
        var lockValue = LockValue.fromType(type, key);
        lock.putIfAbsent(lockValue.key(), new Object());
        return lockValue;
    }

    @Override
    public boolean isLocked(@NotNull ILockValue lockerKey) {
        return lock.contains(lockerKey.key());
    }

    @Override
    public boolean isLocked(@NotNull String key) {
        return lock.contains(key);
    }

    @Override
    public @NotNull String getLockKey(ILockType type, @NotNull String index) {
        return StringBuilderHelper.getBuilder().append(type.getLockCode()).append(index).toString();
    }

    @Override
    public boolean unlock(@NotNull ILockValue key) {
        return lock.remove(key.key()) != null;
    }
}
