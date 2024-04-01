package networking.util;

import io.netty.util.AttributeKey;
import networking.client.INitroClient;

public class GameServerAttributes {

    public static final AttributeKey<INitroClient> CLIENT = AttributeKey.valueOf("NitroClient");

    public static final AttributeKey<ReleaseVersion> RELEASE_VERSION = AttributeKey.valueOf("ReleaseVersion");
    public static final AttributeKey<MachineId> MACHINE_ID = AttributeKey.valueOf("MachineId");
}
