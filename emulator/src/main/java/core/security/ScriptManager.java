package core.security;

import com.google.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

@Singleton
public final class ScriptManager implements IScriptManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private final StringFormatterMessageFactory test = new StringFormatterMessageFactory();

    @Override
    public void report(final SecurityBreachLevel securityBreachLevel, final String message, final Throwable throwable, final Object... args) {
        final var callerInfo = this.getCallerInfo();
        LOGGER.error("[{}] script reported at [{}]: {}", securityBreachLevel.name(), callerInfo,
                LOGGER.getMessageFactory().newMessage(message, args), throwable);
    }

    @Override
    public void report(final SecurityBreachLevel securityBreachLevel, final String message, final Object... args) {
        final var callerInfo = this.getCallerInfo();

        LOGGER.error("[{}] script reported at [{}]: {}", securityBreachLevel.name(), callerInfo, LOGGER.getMessageFactory().newMessage(message, args));
    }

    private CallerInfo getCallerInfo() {
        final var stackTraceElement = Thread.currentThread().getStackTrace()[3];
        final var className = stackTraceElement.getClassName();
        final var methodName = stackTraceElement.getMethodName();
        final var callerLine = stackTraceElement.getLineNumber();
        return new CallerInfo(className, methodName, callerLine);
    }

    private record CallerInfo(String className, String methodName, int line) {
        @Override
        public String toString() {
            return STR."\{this.className}.\{this.methodName}:\{this.line}";
        }
    }
}
