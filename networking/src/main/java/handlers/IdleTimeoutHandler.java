package handlers;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import networking.packets.incoming.guest.PingEvent;
import networking.packets.outgoing.PingComposer;
import networking.util.GameServerAttributes;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class IdleTimeoutHandler extends ChannelDuplexHandler {
    private static final long MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1);
    private static final PingEvent pingEvent = new PingEvent();
    private final long pingScheduleNanos;
    private final long pongTimeoutNanos;
    volatile ScheduledFuture<?> pingScheduleFuture;
    volatile long lastPongTime;// in nanoseconds
    private volatile int state; // 0 - none, 1 - initialized, 2 - destroyed

    public IdleTimeoutHandler(int pingScheduleSeconds, int pongTimeoutSeconds) {
        this.pingScheduleNanos = Math.max(MIN_TIMEOUT_NANOS, TimeUnit.SECONDS.toNanos(pingScheduleSeconds));
        this.pongTimeoutNanos = Math.max(MIN_TIMEOUT_NANOS, TimeUnit.SECONDS.toNanos(pongTimeoutSeconds));
    }

    private void initialize(ChannelHandlerContext ctx) {
        // Avoid the case where destroy() is called before scheduling timeouts.
        // See: https://github.com/netty/netty/issues/143
        switch (state) {
            case 1:
            case 2:
                return;
        }

        state = 1;

        lastPongTime = System.nanoTime();
        if (pingScheduleNanos > 0) {
            pingScheduleFuture = ctx.executor().schedule(new PingScheduledTask(ctx), pingScheduleNanos, TimeUnit.NANOSECONDS);
        }
    }

    private void destroy() {
        state = 2;

        if (pingScheduleFuture != null) {
            pingScheduleFuture.cancel(false);
            pingScheduleFuture = null;
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        if (ctx.channel().isActive() && ctx.channel().isRegistered()) {
            // channelActvie() event has been fired already, which means this.channelActive() will
            // not be invoked. We have to initialize here instead.
            initialize(ctx);
        } else {
            // channelActive() event has not been fired yet.  this.channelActive() will be invoked
            // and initialization will occur there.
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        destroy();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        // Initialize early if channel is active already.
        if (ctx.channel().isActive()) {
            initialize(ctx);
        }
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // This method will be invoked only if this handler was added
        // before channelActive() event is fired.  If a user adds this handler
        // after the channelActive() event, initialize() will be called by beforeAdd().
        initialize(ctx);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        destroy();
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof IncomingPacket packet) {
            if (packet.getHeader() == pingEvent.getHeaderId()) {
                this.lastPongTime = System.nanoTime();
            }
        }
        super.channelRead(ctx, msg);
    }

    private final class PingScheduledTask implements Runnable {
        private final ChannelHandlerContext ctx;

        public PingScheduledTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            if (!ctx.channel().isOpen()) {
                return;
            }

            long currentTime = System.nanoTime();
            if (currentTime - lastPongTime > pongTimeoutNanos) {
                ctx.close();// add a promise here ?
                return;
            }

            var client = (INitroClient) ctx.channel().attr(GameServerAttributes.CLIENT).get();
            if (client != null) {
                client.sendMessage(new PingComposer());
            } else {
                // TODO: CHECK IF THIS WORKS
                ctx.channel().writeAndFlush(new PingComposer().getBuffer());
            }

            pingScheduleFuture = ctx.executor().schedule(this, pingScheduleNanos, TimeUnit.NANOSECONDS);
        }
    }
}