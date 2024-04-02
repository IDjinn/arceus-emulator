package packets.incoming;

import io.netty.channel.ChannelHandlerContext;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;

public abstract class IncomingEvent {
    public abstract int getHeaderId();
    
    
    public void Parse(IncomingPacket packet, INitroClient client){
        
    }


    public void ParseForGuest(IncomingPacket packet, ChannelHandlerContext ctx){
        throw new RuntimeException("Should not be called by base class, it does means it was not overridden by derived class and suggested an implementation error");
    }
}
