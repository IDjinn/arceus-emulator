package core.security;

import com.google.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;
import utils.ReflectionHelpers;

@Singleton
public final class ScriptManager implements IScriptManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private final StringFormatterMessageFactory test = new StringFormatterMessageFactory();

    @Override
    public void report(final SecurityBreachLevel securityBreachLevel, final String message, final Throwable throwable, final Object... args) {
        final var callerInfo = ReflectionHelpers.getCallerInfo();
        LOGGER.error("[{}] script reported at [{}]: {}", securityBreachLevel.name(), callerInfo,
                LOGGER.getMessageFactory().newMessage(message, args), throwable);
    }

    @Override
    public void report(final SecurityBreachLevel securityBreachLevel, final String message, final Object... args) {
        final var callerInfo = ReflectionHelpers.getCallerInfo();

        LOGGER.error("[{}] script reported at [{}]: {}", securityBreachLevel.name(), callerInfo, LOGGER.getMessageFactory().newMessage(message, args));
    }

}
