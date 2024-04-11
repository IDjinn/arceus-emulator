package client;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import io.netty.channel.ChannelHandlerContext;
import networking.client.INitroClient;
import networking.client.INitroClientFactory;

@Singleton
public class NitroClientFactory implements INitroClientFactory {
    @Inject
    Injector injector;
    
    @Override
    public INitroClient create(ChannelHandlerContext ctx) {
        var client = new NitroClient(ctx);
        this.injector.injectMembers(client);

        return client;
    }
}
