package client;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import io.netty.channel.ChannelHandlerContext;
import networking.client.IClient;
import networking.client.IClientFactory;

@Singleton
public class ClientFactory implements IClientFactory {
    @Inject
    Injector injector;
    
    @Override
    public IClient create(ChannelHandlerContext ctx) {
        var client = new Client(ctx);
        this.injector.injectMembers(client);

        return client;
    }
}
