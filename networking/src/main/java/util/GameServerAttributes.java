package util;

import io.netty.util.AttributeKey;
import networking.client.INitroClient;

public class GameServerAttributes {

    public static final AttributeKey<INitroClient> CLIENT = AttributeKey.valueOf("NitroClient");

}
