package networking.client;

import habbo.habbos.IHabbo;
import networking.packets.OutgoingPacket;

import java.util.List;

public interface INitroClient {
    void sendMessage(OutgoingPacket packet);

    void sendMessages(List<OutgoingPacket> messages);

    void sendMessages(OutgoingPacket... messages);

    void flush();

    IHabbo getHabbo();
}
