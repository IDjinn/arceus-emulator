package networking.packets.assertions;

import org.jetbrains.annotations.Nullable;
import utils.result.GameError;
import utils.result.Result;

import java.util.function.Predicate;

public interface IPacketAssertion {
    IPacketAssertion assertInteger(Predicate<Integer> integerPredicate, String debugMessage);

    IPacketAssertion assertInteger(Predicate<Integer> integerPredicate, String debugMessage, @Nullable GameError error);

    IPacketAssertion assertString(IDataAssertion dataAssertion, String debugMessage, @Nullable GameError error);

    IPacketAssertion assertString(IDataAssertion dataAssertion, String debugMessage);

    Result<Boolean, GameError> result();
}
