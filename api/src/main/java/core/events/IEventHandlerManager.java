package core.events;

import core.plugins.IPlugin;

import java.util.List;

public interface IEventHandlerManager {
    boolean registerPluginEvents(final IPlugin pluginInstance, List<Class<?>> classes);

    boolean unregisterPluginEvents(IPlugin plugin);

    void onEvent(IEvent event);
}
