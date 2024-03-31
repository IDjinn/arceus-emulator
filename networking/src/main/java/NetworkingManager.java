import com.google.inject.Inject;
import configuration.IConfigurationManager;
import core.IEmulator;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import networking.INetworkingManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Initializer;

@Initializer
public class NetworkingManager implements INetworkingManager {
    private static Logger logger = LogManager.getLogger();
    @Inject private IEmulator emulator;
    @Inject private IConfigurationManager configurationManager;
    private final ServerBootstrap serverBootstrap;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private final String host;
    private final int port;
    @Inject public NetworkingManager(IConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;

        this.bossGroup = new NioEventLoopGroup(
                configurationManager.getInt("net.boss.threads", 1)
        );
        this.workerGroup = new NioEventLoopGroup(
                configurationManager.getInt("net.worker.threads", 5)
        );
        this.serverBootstrap = new ServerBootstrap();
        host = configurationManager.getString("net.host", "0.0.0.0");
        port = configurationManager.getInt("net.port", 5000);
        
        this.serverBootstrap.group(this.bossGroup, this.workerGroup);
        this.serverBootstrap.channel(NioServerSocketChannel.class);
        this.serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        this.serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        this.serverBootstrap.childOption(ChannelOption.SO_REUSEADDR, true);
        this.serverBootstrap.childOption(ChannelOption.SO_RCVBUF, 4096);
        this.serverBootstrap.childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(4096));
        this.serverBootstrap.childOption(ChannelOption.ALLOCATOR, new UnpooledByteBufAllocator(false));

        this.serverBootstrap.childHandler(new NetworkChannelInitializer());
        ChannelFuture bindResult =this.serverBootstrap.bind(this.host, this.port);
        try {
            bindResult.sync();
            if (bindResult.isSuccess()){
                logger.info("Listening on '{}:{}'", this.host, this.port);
            }
            else {
                logger.error("Failed to listen on '{}:{}'", this.host, this.port);
            }
        } catch (InterruptedException e) {
            emulator.shutdown();
        } 
    }

    @Override
    public void init() throws InterruptedException {
        
    }

    @Override
    public void destroy() {

    }
}
