package core.events;

import core.plugins.IPlugin;

import java.util.List;

public interface IEventHandlerManager {
    public boolean registerPluginEvents(List<Class<?>> classes);

    public boolean unregisterPluginEvents(IPlugin plugin);
}
