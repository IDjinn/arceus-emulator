package core.plugins;

import core.IHotelService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IPluginManager extends IHotelService {
    Map<Class<? extends IPlugin>, IPlugin> getPlugins();

    Optional<? extends IPlugin> getPlugin(Class<? extends IPlugin> pluginClass);

    boolean registerPlugin(IPlugin instance, final List<Class<?>> pluginClasses);

    boolean destroyPlugin(Class<IPlugin> clazz);
}
