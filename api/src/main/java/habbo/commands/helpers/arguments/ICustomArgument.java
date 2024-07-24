package habbo.commands.helpers.arguments;

import habbo.commands.ICommandContext;

import java.util.Optional;
import java.util.function.Function;

public interface ICustomArgument extends ICommandArgument {
    Function<ICommandContext, Optional<Object>> getHandler();
}
