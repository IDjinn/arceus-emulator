package networking.client;

import networking.packets.OutgoingPacket;

import java.util.List;

public interface INitroClient {
    void sendMessage(OutgoingPacket packet);

    void sendMessages(List<OutgoingPacket> messages);
}
