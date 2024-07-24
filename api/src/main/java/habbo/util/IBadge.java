package habbo.util;

import org.jetbrains.annotations.Nullable;

public interface IBadge {
    String getCode();

    @Nullable
    String getName();

    @Nullable
    String getDescription();
}
