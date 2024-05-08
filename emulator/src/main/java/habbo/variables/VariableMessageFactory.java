package habbo.variables;

import com.google.inject.Singleton;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;

import java.util.List;
import java.util.regex.Pattern;

@Singleton
public final class VariableMessageFactory extends MessageFormatMessageFactory implements IVariableMessageFactory {
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{([^}]*)\\}");

    @Override
    public String format(final String message) {
        return new MessageWithVariables(message).getFormattedMessage();
    }

    public String format(String message, List<IVariable> variables) {
        return new MessageWithVariables(message, variables).getFormattedMessage();
    }
}
