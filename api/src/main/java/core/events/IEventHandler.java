package core.events;

import core.plugins.IPlugin;

import java.util.List;

public interface IEventHandler {
    boolean registerPluginEvents(final IPlugin pluginInstance, List<Class<?>> classes);

    boolean unregisterPluginEvents(IPlugin plugin);

    /**
     * @param classes classes with static methods listeners to register in event handler
     * @return
     */
    boolean registerEvents(List<Class<?>> classes);

    boolean unregisterEvents(List<Class<?>> classes);

    <TListener> boolean subscribe(TListener listener);

    void onEvent(IEvent event);
}
