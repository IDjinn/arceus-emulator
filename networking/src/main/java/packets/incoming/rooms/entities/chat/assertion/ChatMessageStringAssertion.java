package packets.incoming.rooms.entities.chat.assertion;

import networking.packets.assertions.IDataAssertion;
import utils.result.GameError;
import utils.result.Result;

public final class ChatMessageStringAssertion implements IDataAssertion<String> {
    private static final int MAX_LENGTH = 5;
    private static final boolean TRIM = true;
    public static IDataAssertion instance = new ChatMessageStringAssertion();

    @Override
    public Result<Boolean, GameError> assertion(String value) {
        if (TRIM) {
            return Result.of(value.trim().length() <= MAX_LENGTH);
        }
        // TODO FILTERING ETC
        return Result.of(value.length() <= MAX_LENGTH);
    }
}
