package org.emulator.wireds;

import com.google.inject.Singleton;
import core.events.EventListener;
import core.events.EventListenerPriority;
import habbo.rooms.events.OnRoomLoaded;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Singleton
public class RoomInjector {
    private static final Logger LOGGER = LogManager.getLogger();

    @EventListener(getEventListenerPriority = EventListenerPriority.Medium)
    public void onRoomLoaded(OnRoomLoaded onRoomLoaded) {
        LOGGER.info("Room loaded");
    }
}
