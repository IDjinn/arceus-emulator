package utils;

public final class ReflectionHelpers {
    private ReflectionHelpers() {
    }

    public static CallerInfo getCallerInfo() {
        // [0]=getStackTrace, [1]=getCallerInfo, [2]=your caller
        final var stackTraceElement = Thread.currentThread().getStackTrace()[2];
        final var className = stackTraceElement.getClassName();
        final var methodName = stackTraceElement.getMethodName();
        final var callerLine = stackTraceElement.getLineNumber();
        return new CallerInfo(className, methodName, callerLine);
    }

    public record CallerInfo(String className, String methodName, int line) {
        @Override
        public String toString() {
            return className + "." + methodName + ":" + line;
        }
    }
}
