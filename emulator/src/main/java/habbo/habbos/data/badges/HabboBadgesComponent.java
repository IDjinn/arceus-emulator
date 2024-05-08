package habbo.habbos.data.badges;

import com.google.inject.Inject;
import habbo.habbos.IHabbo;
import org.jetbrains.annotations.Nullable;
import storage.repositories.habbo.IHabboBadgesRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HabboBadgesComponent implements IHabboBadgesComponent {
    private final Map<String, IHabboBadge> badges;
    private final Map<Integer, IHabboBadge> slots;
    private final IHabbo habbo;
    @Inject
    private IHabboBadgesRepository badgesRepository;

    public HabboBadgesComponent(final IHabbo habbo) {
        this.habbo = habbo;
        this.badges = new ConcurrentHashMap<>();
        this.slots = new HashMap<>();
    }

    @Override
    public Map<String, IHabboBadge> getBadges() {
        return this.badges;
    }

    @Override
    public @Nullable IHabboBadge getBadge(final String badgeCode) {
        return this.badges.get(badgeCode);
    }

    @Override
    public void addBadge(final IHabboBadge badge) {
        this.badges.put(badge.getCode(), badge);

    }

    @Override
    public void addBadges(final List<IHabboBadge> badge) {
        for (final IHabboBadge habboBadge : badge) {
            this.addBadge(habboBadge);
        }
    }

    @Override
    public void removeBadge(final String badgeCode) {
        this.badges.remove(badgeCode);
    }

    @Override
    public void removeBadges(final List<String> badgeCode) {
        for (final String code : badgeCode) {
            this.removeBadge(code);
        }
    }

    @Override
    public Map<Integer, IHabboBadge> getProfileEquippedBadges() {
        return this.slots;
    }

    @Override
    public void init() {
        this.badgesRepository.getAllBadges(result -> {
            if (result == null) return;

            final var code = result.getString("badge_code");
            final var slot = result.getInt("slot_id");
            final var badge = new HabboBadge(code, slot);
            this.badges.put(badge.getCode(), badge);
        }, this.getHabbo().getData().getId());
    }

    @Override
    public IHabbo getHabbo() {
        return this.habbo;
    }
}
