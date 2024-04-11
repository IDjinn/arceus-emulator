package core.security;

public interface IScriptManager {

    void report(final SecurityBreachLevel securityBreachLevel, final String message, final Throwable throwable, final Object... args);

    void report(final SecurityBreachLevel securityBreachLevel, final String message, final Object... args);
}
