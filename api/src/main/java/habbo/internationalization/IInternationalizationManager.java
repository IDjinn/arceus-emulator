package habbo.internationalization;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public interface IInternationalizationManager {
    @NotNull String getLocalizedString(@NotNull LocalizedString localizedString, Locale locale);

    @NotNull String[] getAllLocalizedStrings(@NotNull LocalizedString localizedString);
}
