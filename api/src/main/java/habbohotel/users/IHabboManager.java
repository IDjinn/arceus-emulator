package habbohotel.users;

import io.netty.channel.ChannelHandlerContext;
import org.jetbrains.annotations.NotNull;

public interface IHabboManager {
    public boolean tryLoginWithSSO(@NotNull ChannelHandlerContext ctx, @NotNull String sso);
}
