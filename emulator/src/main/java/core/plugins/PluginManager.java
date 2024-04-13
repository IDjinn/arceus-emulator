package core.plugins;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class PluginManager implements IPluginManager {
    private final Logger logger = LogManager.getLogger();
    private final Map<Class<? extends IPlugin>, IPlugin> plugins;

    public PluginManager() {
        this.plugins = new HashMap<>();
    }

    @Override
    public Map<Class<? extends IPlugin>, IPlugin> getPlugins() {
        return this.plugins;
    }

    @Override
    public Optional<? extends IPlugin> getPlugin(final Class<? extends IPlugin> pluginClass) {
        return Optional.ofNullable(this.plugins.get(pluginClass));
    }

    @Override
    public boolean registerPlugin(final IPlugin instance) {
        if (this.plugins.containsKey(instance.getClass()))
            return false;

        instance.init();
        this.plugins.put(instance.getClass(), instance);
        this.logger.info("plugin {}, made by {} at version {} was successfully registered in hotel.",
                instance.getName(),
                instance.getAuthor(),
                instance.getVersion()
        );
        return true;
    }

    @Override
    public boolean destroyPlugin(final Class<IPlugin> clazz) {
        final var plugin = this.plugins.get(clazz);
        if (plugin == null)
            return false;

        plugin.destroy();
        this.plugins.remove(plugin.getClass());
        this.logger.info("plugin {}, made by {} at version {} was successfully destroyed.",
                plugin.getName(),
                plugin.getAuthor(),
                plugin.getVersion()
        );
        return true;
    }

    @Override
    public void init() {
        final var pluginsDirectory = new File("plugins");
        if (!pluginsDirectory.exists()) pluginsDirectory.mkdir();

        for (final var pluginFolder : Objects.requireNonNull(pluginsDirectory.listFiles(File::isDirectory))) {
            for (final var plugin : Arrays.stream(Objects.requireNonNull(pluginFolder.listFiles())).filter(file -> file.toPath().endsWith(".jar")).toList()) {
                try {
                    final var classLoader = URLClassLoader.newInstance(new URL[]{plugin.toURI().toURL()});
                    final var pluginConfiguration = new Properties();
                    pluginConfiguration.load(classLoader.getResourceAsStream("plugin.ini"));
                    final var pluginClass = classLoader.loadClass(pluginConfiguration.getProperty("plugin.entrypoint"));
                    final var pluginInstance = ((IPlugin) pluginClass.getDeclaredConstructor().newInstance());

                    this.registerPlugin(pluginInstance);
                } catch (Exception e) {
                    this.logger.error("error while creating plugin instance", e);
                }
            }
        }
    }

    @Override
    public void destroy() {

    }
}
