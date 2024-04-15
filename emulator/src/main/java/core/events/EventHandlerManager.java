package core.events;

import com.google.inject.Inject;
import com.google.inject.Injector;
import core.plugins.IPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

public class EventHandlerManager implements IEventHandlerManager {
    private final Logger logger = LogManager.getLogger();
    private final Map<Class<? extends IEvent>, List<ListenerCallback>> listeners;
    private final Injector injector;

    @Inject
    public EventHandlerManager(Injector injector) {
        this.injector = injector;
        this.listeners = new HashMap<>();
    }

    private List<Class<?>> getClassesFromPackage(String packageName) throws IOException, ClassNotFoundException {
        final var classLoader = Thread.currentThread().getContextClassLoader();
        final var path = packageName.replace('.', '/');
        final var resources = classLoader.getResources(path);
        final var directories = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            var resource = resources.nextElement();
            directories.add(new File(resource.getFile()));
        }

        final var classes = new ArrayList<Class<?>>();
        for (File directory : directories)
            classes.addAll(this.findClasses(directory, packageName));

        return classes;
    }


    private List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        final var classes = new ArrayList<Class<?>>();
        if (!directory.exists())
            return classes;

        var files = directory.listFiles();
        if (files == null)
            return classes;

        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(this.findClasses(file, STR."\{packageName}.\{file.getName()}"));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }


    private List<Method> getEventListenersOf(List<Class<?>> classes) throws IOException, ClassNotFoundException {
        final var listenerMethods = new ArrayList<Method>();
        for (final var clazz : classes) {
            for (final var method : clazz.getDeclaredMethods()) {
                if (method.getParameterCount() != 1) continue;
                
                final var annotations = method.getDeclaredAnnotations();
                for (final var annotation : annotations) {
                    if (annotation.annotationType().equals(EventListener.class)) {
                        listenerMethods.add(method);
                        break;
                    }
                }
            }
        }
        return listenerMethods;
    }

    @Override
    public boolean registerPluginEvents(final IPlugin pluginInstance, final List<Class<?>> classes) { // TODO PROPER ERROR HANDLING
        try {
            for (final var listenerMethod : this.getEventListenersOf(classes)) {
                var eventType = Arrays.stream(listenerMethod.getParameterTypes()).findFirst();
                if (eventType.isPresent() && IEvent.class.isAssignableFrom(eventType.get())) {
                    final var priority =
                            ((EventListener) Arrays.stream(listenerMethod.getDeclaredAnnotations()).filter(a -> a.annotationType().equals(EventListener.class)).findFirst().get()).getEventListenerPriority();
                    final var instance = pluginInstance.getInjector().getInstance(listenerMethod.getDeclaringClass());
                    this.listeners.computeIfAbsent((Class<? extends IEvent>) eventType.get(), k -> new ArrayList<>()).add(new ListenerCallback(pluginInstance, instance, listenerMethod, priority));
                }
            }
            for (var listenerMethods : this.listeners.values()) {
                Collections.sort(listenerMethods);
            }
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onEvent(final IEvent event) {
        final var listeners = this.listeners.get(event.getClass());
        if (listeners == null)
            return;

        for (final var listener : listeners) {
            try {
                listener.method.invoke(listener.instance, event);
            } catch (Exception e) {
                this.logger.error("failed to invoke event listener {}: {}", listener.method.getClass().getName(), e.getMessage(), e);
            }
        }
    }

    @Override
    public boolean unregisterPluginEvents(final IPlugin plugin) {
        return false;
    }

    record ListenerCallback(IPlugin pluginInstance, Object instance, Method method,
                            EventListenerPriority priority) implements Comparable<ListenerCallback> {
        @Override
        public int compareTo(@NotNull final EventHandlerManager.ListenerCallback o) {
            return Integer.compare(o.priority.ordinal(), this.priority.ordinal());
        }
    }
}
