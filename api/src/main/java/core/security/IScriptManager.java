package core.security;

public interface IScriptManager {

    public void report(final SecurityBreachLevel securityBreachLevel, final String message, final Throwable throwable, final Object... args);

    public void report(final SecurityBreachLevel securityBreachLevel, final String message, final Object... args);
}
