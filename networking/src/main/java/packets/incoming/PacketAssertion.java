package packets.incoming;

import networking.packets.IIncomingPacket;
import networking.packets.assertions.IDataAssertion;
import networking.packets.assertions.IPacketAssertion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import utils.result.GameError;
import utils.result.Result;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class PacketAssertion implements IPacketAssertion {
    private static final Logger LOGGER = LogManager.getLogger();
    private final IIncomingPacket packet;
    private final List<GameError> errors;

    public PacketAssertion(IIncomingPacket packet) {
        this.packet = packet;
        this.errors = new LinkedList<>();
    }

    @Override
    public IPacketAssertion assertInteger(final Predicate<Integer> integerPredicate, final String debugMessage) {
        return this.assertInteger(integerPredicate, debugMessage, null);
    }

    @Override
    public IPacketAssertion assertInteger(final Predicate<Integer> integerPredicate, final String debugMessage, @Nullable final GameError error) {
        if (integerPredicate.test(this.packet.readInt())) return this;

        if (error != null) this.errors.add(error);
        else this.errors.add(this.assertionFailed(debugMessage, integerPredicate));
        return this;
    }

    @Override
    public IPacketAssertion assertString(final IDataAssertion dataAssertion, final String debugMessage, @Nullable final GameError error) {
        if (dataAssertion.assertion(this.packet.readString()).isSuccess()) return this; // TODO: DATA MUST BE READ 
        // PROPERTY

        if (error != null) this.errors.add(error);
        else this.errors.add(this.assertionFailed(debugMessage, null));
        return this;
    }

    @Override
    public IPacketAssertion assertString(final IDataAssertion dataAssertion, final String debugMessage) {
        return this.assertString(dataAssertion, debugMessage, null);
    }

    @Override
    public Result<Boolean, GameError> result() {
        this.packet.getBuffer().resetReaderIndex();
        if (this.errors.isEmpty()) return Result.ok(true);

        return Result.error(this.errors.getFirst());
    }

    private GameError assertionFailed(String debugMessage, Predicate predicate) {
        return new GameError("error.generic.assertion", LOGGER.getMessageFactory().newMessage("Assertion failed: {} must be {}", debugMessage, predicate).getFormattedMessage());
    }
}
