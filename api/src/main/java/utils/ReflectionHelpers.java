package utils;

import org.jetbrains.annotations.NotNull;

public final class ReflectionHelpers {
    public static CallerInfo getCallerInfo() {
        final var stackTraceElement = Thread.currentThread().getStackTrace()[3];
        final var className = stackTraceElement.getClassName();
        final var methodName = stackTraceElement.getMethodName();
        final var callerLine = stackTraceElement.getLineNumber();
        return new CallerInfo(className, methodName, callerLine);
    }

    public record CallerInfo(String className, String methodName, int line) {
        @Override
        public @NotNull String toString() {
            return StringBuilderHelper.getBuilder().append(this.className).append('.').append(this.methodName).append('.').append(this.line).toString();
        }
    }
}
