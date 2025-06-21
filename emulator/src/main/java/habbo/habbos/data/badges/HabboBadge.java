package habbo.habbos.data.badges;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Getter
@Setter
public class HabboBadge implements IHabboBadge {
    private final String code;
    private int slot;

    public HabboBadge(final String code, final int slot) {
        this.code = code;
        this.slot = slot;
    }

    @Override
    public Optional<Integer> getSlot() {
        return this.slot > 0 ? Optional.of(this.slot) : Optional.empty();
    }
    @Override
    public @Nullable String getName() {
        return null;
    }

    @Override
    public @Nullable String getDescription() {
        return null;
    }
}
