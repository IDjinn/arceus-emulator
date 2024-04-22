package habbo.commands;

import com.google.inject.AbstractModule;

public class CommandModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(ICommandManager.class).to(CommandManager.class);
        this.bind(ICommandHelpers.class).to(CommandHelpers.class);
    }
}
