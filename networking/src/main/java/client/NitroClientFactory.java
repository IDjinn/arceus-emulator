package client;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import habbo.habbos.IHabboFactory;
import io.netty.channel.ChannelHandlerContext;
import networking.client.INitroClient;
import networking.client.INitroClientFactory;

@Singleton
public class NitroClientFactory implements INitroClientFactory {
    @Inject
    IHabboFactory habboFactory;
    @Inject
    Injector injector;
    
    @Override
    public INitroClient create(ChannelHandlerContext ctx) {
        var client = new NitroClient(ctx, habboFactory);
        injector.injectMembers(client);
        return client;
    }
}
