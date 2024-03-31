package networking.client;

import networking.packets.OutgoingPacket;

public interface INitroClient {
    void sendMessage(OutgoingPacket packet);
}
