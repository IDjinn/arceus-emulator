package habbo.habbos.data.badges;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

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
    public void setSlot(final int slot) {
        this.slot = slot;
    }

    @Override
    public String getCode() {
        return this.code;
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
