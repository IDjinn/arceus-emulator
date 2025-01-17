# Arceus Pack

## What is it?

The **Arceus Pack** is a clean and expandable emulator designed for Habbo retros. It prioritizes ease of editing, maintenance, and updates, delivering a superior gameplay experience with enhancements in **Nitro** for optimal performance.

This project does not aim to replicate Habbo 1:1, as retros are meant to innovate and expand beyond the original. Everything implemented—whether in the emulator, client, or based on player suggestions—is carefully considered and integrated. The emulator's modular design allows you to toggle features, use plugins, and configure behavior without relying on hacks or unstable modifications often seen in other emulators.

---

## Currently Implemented Features

- **Basic Interactions**: Sitting, lying down, using rollers, teleporting.
- **Command Manager**: The structure is complete, but more commands are being added.
- **Navigator**: A functional in-game room navigator.
- **Catalog**: Fully functional catalog integration.
- **Inventory & Badges**: Player inventory and badge management.
- **Plugin Manager**: Modular plugin support for easy feature additions.
- **Event Handler**: Scoped event management for flexible subscription and handling.
- **Configuration & Localization**: Built-in localization and highly configurable settings.

---

## Technical Details

- **Modular Architecture**: The emulator is divided into multiple modules, sharing an API module to expose interfaces between core components and plugins.
- **Guice Dependency Injection**: Each module injects its own bindings, and plugins are integrated as children of the emulator's root injector.
- **Task Handler**: Game cycles are managed by task handlers. For instance, rooms have dedicated handlers, where entities and furniture register tasks to execute within the game interval.
- **Scoped Event Handlers**: Events are scoped, meaning you must subscribe to specific instances (e.g., a room) to listen for and handle events.
- **Custom Components**: Rooms support custom components that integrate seamlessly with their logic.
- **Wired System**: A plugin-based wired system with a flexible implementation. It differs significantly from the standard wired handling but can be reverted to match the "original Habbo" style if desired.
- **Optimized Pathfinder**: Uses pooled objects by default for efficiency.
- **Improved Serialization**: Extra data serialization fixed—no more unstable hacks for client compatibility.
- **WIP Packet Validation**: Ensures packet assertions and validations are implemented properly.
- **WIP Command Structure**: Improved command context and structure.

---

## Command Example

```java
public class AboutCommand implements ICommand {

    private static final LocalizedString name = LocalizedString.of("command.about.name");
    private static final LocalizedString[] alias = new LocalizedString[]{};
    private static final LocalizedString description = LocalizedString.of("command.about.description");
    private static final LocalizedString response = LocalizedString.of("command.about.response", "hello world!");

    @Override
    public void execute(final ICommandContext ctx) {
        ctx.whisper(response);
    }

    @Override
    public @NotNull LocalizedString getName() {
        return name;
    }

    @Override
    public @NotNull LocalizedString[] getAlias() {
        return alias;
    }

    @Override
    public @NotNull LocalizedString getDescription() {
        return description;
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }
}
```
## Credits
- Djinn (me)
- Nicollas (helped in early stages, not longer as dev)
