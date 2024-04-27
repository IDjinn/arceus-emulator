package packets.incoming;

import io.netty.channel.ChannelHandlerContext;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
import utils.result.GameError;
import utils.result.Result;

public abstract class IncomingEvent {
    public abstract int getHeaderId();

    public Result<Boolean, GameError> validate(IIncomingPacket packet, IClient client) {
        return Result.ok(true);
    }

    public void parse(IIncomingPacket packet, IClient client) {
        
    }

    public void parseForGuest(IIncomingPacket packet, ChannelHandlerContext ctx) {
        throw new RuntimeException("Should not be called by base class, it does means it was not overridden by derived class and suggested an implementation error");
    }
}
