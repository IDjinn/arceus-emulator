package habbo.internationalization;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

public class InternationalizationManager implements IInternationalizationManager {
    private static final String BASE_NAME = "texts";

    public InternationalizationManager() {
    }

    @Override
    public @NotNull String getLocalizedString(@NotNull final LocalizedString localizedString, final Locale locale) {
        try {
            return ResourceBundle.getBundle(BASE_NAME, locale).getString(localizedString.getKey());
        } catch (Exception ignored) {
            return localizedString.getDefaultValue().orElse(localizedString.getKey());
        }
    }

    @Override
    public @NotNull String[] getAllLocalizedStrings(@NotNull final LocalizedString localizedString) {
        return new String[]{this.getLocalizedString(localizedString, Locale.ENGLISH)};
    }
}
