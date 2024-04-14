package core.events;

import core.plugins.IPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

public class EventHandlerManager implements IEventHandlerManager {

    private final Map<Class<? extends IEvent>, List<Method>> listeners;

    public EventHandlerManager() {
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
    public boolean registerPluginEvents(final List<Class<?>> classes) {
        try {
            for (final var listener : this.getEventListenersOf(classes)) {
                var eventType = Arrays.stream(listener.getParameterTypes()).findFirst();
                if (eventType.isPresent() && eventType.get().isAssignableFrom(IEvent.class)) {
                    this.listeners.computeIfAbsent((Class<? extends IEvent>) eventType.get(), k -> new ArrayList<>()).add(listener);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean unregisterPluginEvents(final IPlugin plugin) {
        return false;
    }
}
