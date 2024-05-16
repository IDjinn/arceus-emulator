package habbo.commands.rooms.building;

import habbo.commands.ICommand;
import habbo.internationalization.LocalizedString;
import org.jetbrains.annotations.NotNull;

public class BuilderTeleportCommand implements ICommand {
    private static final LocalizedString name = LocalizedString.of("command.rooms.building.builder-teleport.name");
    private static final LocalizedString description = LocalizedString.of("command.rooms.building.builder-teleport.description");
    private static final LocalizedString[] alias = new LocalizedString[]{};

    @Override
    public @NotNull LocalizedString getName() {
        return name;
    }

    @Override
    public @NotNull LocalizedString[] getAlias() {
        return new LocalizedString[0];
    }

    @Override
    public @NotNull LocalizedString getDescription() {
        return description;
    }
}
