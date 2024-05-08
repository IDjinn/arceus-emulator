package habbo.habbos.data.badges;

import habbo.habbos.IHabboComponent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public interface IHabboBadgesComponent extends IHabboComponent {
    Map<String, IHabboBadge> getBadges();

    @Nullable
    IHabboBadge getBadge(String badgeCode);

    void addBadge(IHabboBadge badge);

    void addBadges(List<IHabboBadge> badge);

    void removeBadge(String badgeCode);

    void removeBadges(List<String> badgeCode);

    Map<Integer, IHabboBadge> getProfileEquippedBadges();
}
