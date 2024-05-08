package habbo.internationalization;

import com.google.inject.Inject;
import habbo.variables.IVariableMessageFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

public class InternationalizationManager implements IInternationalizationManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String BASE_NAME = "texts";
    private final IVariableMessageFactory variableMessageFactory;

    @Inject
    public InternationalizationManager(final IVariableMessageFactory variableMessageFactory) {
        this.variableMessageFactory = variableMessageFactory;
    }

    @Override
    public @NotNull String getLocalizedString(@NotNull final LocalizedString localizedString, final Locale locale) {
        try {
            final var localeString = ResourceBundle.getBundle(BASE_NAME, locale).getString(localizedString.getKey());
            return this.variableMessageFactory.format(localeString, localizedString.getVariables());
        } catch (Exception ignored) {
            return localizedString.getDefaultValue().orElse(this.variableMessageFactory.format(localizedString.getKey(), localizedString.getVariables()));
        }
    }

    @Override
    public @NotNull String[] getAllLocalizedStrings(@NotNull final LocalizedString localizedString) {
        return new String[]{this.getLocalizedString(localizedString, Locale.ENGLISH)};
    }
}
