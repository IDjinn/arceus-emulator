package storage.repositories.emulator;

import storage.results.IConnectionResultConsumer;

public interface IEmulatorRepository {
    void loadAllSettings(IConnectionResultConsumer consumer);
}
