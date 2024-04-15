package org.emulator.wireds;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import core.events.EventListener;
import core.events.EventListenerPriority;
import habbo.rooms.events.OnRoomPreLoaded;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.emulator.wireds.roomWiredComponent.WiredManager;

@Singleton
public class RoomInjector {
    private static final Logger LOGGER = LogManager.getLogger();
    @Inject
    private Injector injector;

    @EventListener(priority = EventListenerPriority.Medium)
    public void onRoomPreLoaded(OnRoomPreLoaded onRoomPreLoaded) {
        final var wiredManager = new WiredManager(onRoomPreLoaded.room());
        this.injector.injectMembers(wiredManager);
        onRoomPreLoaded.room().registerCustomComponent(WiredManager.class, wiredManager);
    }
}
