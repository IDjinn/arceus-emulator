package packets.dto.outgoing.badges;

import habbo.habbos.data.badges.IHabboBadge;
import networking.packets.IPacketDTO;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public record UserBadgesComposerDTO(int userId, Map<Integer, String> badges) implements IPacketDTO {
    public static UserBadgesComposerDTO of(int userId, Map<Integer, IHabboBadge> badges){
            return new UserBadgesComposerDTO(userId,  badges.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> entry.getValue().getCode()
                    )));
    }
}
