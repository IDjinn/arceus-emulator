package core.plugins;

import com.google.inject.Inject;
import com.google.inject.Injector;
import core.IEmulator;
import core.IHotel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginManager implements IPluginManager {
    private final Logger logger = LogManager.getLogger();
    private final Map<Class<? extends IPlugin>, IPlugin> plugins;
    private final Injector injector;
    private final IHotel hotel;
    private final IEmulator emulator;

    @Inject
    public PluginManager(Injector injector, IHotel hotel, IEmulator emulator) {
        this.injector = injector;
        this.hotel = hotel;
        this.emulator = emulator;
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
    public boolean registerPlugin(final IPlugin instance, final List<Class<?>> pluginClasses) {
        if (this.plugins.containsKey(instance.getClass()))
            return false;

        final var pluginInjector = this.injector.createChildInjector(instance);
        pluginInjector.injectMembers(instance);
        this.hotel.getEventHandlerManager().registerPluginEvents(instance, pluginClasses);
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

    private static List<Class<?>> getClassesInPackage(String packageName, String jarFilePath, ClassLoader loader) throws Exception {
        final var jarFile = new JarFile(jarFilePath);
        final var entries = jarFile.entries();

        packageName = packageName.replace(".", "/");
        final var classes = new ArrayList<Class<?>>();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (!entry.getName().startsWith(packageName)) continue;
            if (entry.getName().endsWith(".class")) {
                String className = entry.getName().replace('/', '.').substring(0, entry.getName().length() - 6);
                classes.add(Class.forName(className, true, loader));
            }
        }

        return classes;
    }

    @Override
    public void init() {
        final var pluginsDirectory = new File("plugins");
        if (!pluginsDirectory.exists())
            pluginsDirectory.mkdir();

        for (final var pluginFolder : Objects.requireNonNull(pluginsDirectory.listFiles(File::isDirectory))) {
            for (final var pluginFile : Arrays.stream(Objects.requireNonNull(pluginFolder.listFiles())).toList()) {
                if (!pluginFile.getPath().endsWith(".jar") && !pluginFile.getPath().endsWith(".class")) continue;
                
                try {
                    final var classLoader = URLClassLoader.newInstance(new URL[]{pluginFile.toURI().toURL()},
                            this.getClass().getClassLoader());
                    final var pluginConfiguration = new Properties();
                    pluginConfiguration.load(new FileReader(STR."\{pluginFile.getParent()}\\plugin.ini"));
                    final var pluginClass = classLoader.loadClass(pluginConfiguration.getProperty("plugin.entrypoint"));
                    final var pluginInstance = ((IPlugin) pluginClass.getDeclaredConstructor().newInstance());
                    final var pluginClasses = getClassesInPackage(pluginClass.getPackageName(), pluginFile.getPath(),
                            classLoader);
                    this.registerPlugin(pluginInstance, pluginClasses);
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
