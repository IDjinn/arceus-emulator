package habbo.habbos.data.badges;

import habbo.util.IBadge;

import java.util.Optional;

public interface IHabboBadge extends IBadge {
    Optional<Integer> getSlot();

    void setSlot(int slot);
}
