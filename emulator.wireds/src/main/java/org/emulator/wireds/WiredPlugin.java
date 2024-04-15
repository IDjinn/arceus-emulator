package org.emulator.wireds;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import core.plugins.IPlugin;
import habbo.rooms.components.objects.items.IRoomItemFactory;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.emulator.wireds.boxes.triggers.WiredTriggerEntitySayKeyword;

@Singleton
public class WiredPlugin extends AbstractModule implements IPlugin {
    private static final String author = "IDjinn";
    private static final ComparableVersion version = new ComparableVersion("1.0.0");
    private static final String description = "All habbo original wireds and some customs";
    private static final String name = "wireds-plugin";
    @Inject
    private Injector injector;

    @Inject
    private IRoomItemFactory roomItemFactory;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public ComparableVersion getVersion() {
        return version;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void init() {
        this.roomItemFactory.registerInteraction(
                WiredTriggerEntitySayKeyword.InteractionName,
                WiredTriggerEntitySayKeyword.class
        );
    }

    
    @Override
    public void destroy() {

    }

    @Override
    public Injector getInjector() {
        return this.injector;
    }

    @Override
    protected void configure() {
        this.bind(RoomInjector.class).asEagerSingleton();
    }
}
