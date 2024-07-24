package networking.util;

import io.netty.util.AttributeKey;
import networking.client.IClient;

public final class GameNetowrkingAttributes {

    public static final AttributeKey<IClient> CLIENT = AttributeKey.valueOf("NitroClient");

    public static final AttributeKey<ReleaseVersion> RELEASE_VERSION = AttributeKey.valueOf("ReleaseVersion");
    public static final AttributeKey<MachineId> MACHINE_ID = AttributeKey.valueOf("MachineId");
}
