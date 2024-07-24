package networking.client;

import io.netty.channel.ChannelHandlerContext;

public interface IClientFactory {
    IClient create(ChannelHandlerContext ctx);
}
