package utils.result;

import org.jetbrains.annotations.Nullable;

public final class GameError {
    private final String code;
    private final @Nullable String message;

    public GameError(String code, @Nullable String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
