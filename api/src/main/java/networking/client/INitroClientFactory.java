package networking.client;

import io.netty.channel.ChannelHandlerContext;

public interface INitroClientFactory {

    INitroClient create(ChannelHandlerContext ctx);
}
