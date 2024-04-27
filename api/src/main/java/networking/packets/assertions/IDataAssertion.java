package networking.packets.assertions;

import utils.result.GameError;
import utils.result.Result;

public interface IDataAssertion<Type> {
    Result<Boolean, GameError> assertion(final String value);
}
