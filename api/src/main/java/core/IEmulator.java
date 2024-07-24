package core;

import com.google.inject.Module;

public interface IEmulator extends Module {
    void start();
    void shutdown();
}
