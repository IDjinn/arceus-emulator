package networking.client;

import habbohotel.users.IHabbo;
import networking.packets.OutgoingPacket;

import java.util.List;

public interface INitroClient {
    void sendMessage(OutgoingPacket packet);

    void sendMessages(List<OutgoingPacket> messages);

    void sendMessages(OutgoingPacket... messages);

    void flush();

    IHabbo getHabbo();
}
