package packets;

import com.google.inject.AbstractModule;
import networking.packets.IncomingEvent;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;

public class AutoBindIncomingEventsModule extends AbstractModule {
    @Override
    protected void configure() {
        Reflections reflections = new Reflections(IncomingEvent.class.getPackage().getName());
        for (Class<?> clazz : reflections.getSubTypesOf(IncomingEvent.class)) {
            if (Modifier.isAbstract(clazz.getModifiers())) continue;
            this.bind((Class<? extends IncomingEvent>) clazz);
        }
    }
}