package networking.util;

import com.google.inject.Provider;
import networking.packets.incoming.IncomingEvent;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IncomingEventAsListProvider implements Provider<List<Class<? extends IncomingEvent>>> {
    private final Reflections reflections = new Reflections(IncomingEvent.class.getPackage().getName());


    @Override
    public List<Class<? extends IncomingEvent>> get() {
        List<Class<? extends IncomingEvent>> eventClasses = new ArrayList<>();
        Set<Class<? extends IncomingEvent>> subTypes = reflections.getSubTypesOf(IncomingEvent.class);

        for (Class<? extends IncomingEvent> clazz : subTypes) {
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                eventClasses.add(clazz);
            }
        }

        return eventClasses;
    }
}
