package habbo.variables;

import org.apache.logging.log4j.message.Message;
import utils.StringBuilderHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public final class MessageWithVariables implements Message {
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{([^}]*)\\}",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private final String messageTemplate;
    private final Map<String, IVariable> variables;

    public MessageWithVariables(final String messageTemplate) {
        this.messageTemplate = messageTemplate;
        this.variables = new HashMap<>();
    }

    public MessageWithVariables(final String messageTemplate, final List<IVariable> variables) {
        this.messageTemplate = messageTemplate;
        this.variables = new HashMap<>();
        for (final var variable : variables) {
            this.variables.put(variable.getKey(), variable);
        }
    }

    @Override
    public String getFormattedMessage() {
        final var matcher = VARIABLE_PATTERN.matcher(this.messageTemplate);
        final var sb = StringBuilderHelper.getBuilder();
        while (matcher.find()) {
            final var variableName = matcher.group(1);
            final var variable = this.variables.get(variableName);
            if (variable == null || variable.getValue() == null) continue;

            matcher.appendReplacement(sb, String.valueOf(variable.getValue()));
        }
        return matcher.appendTail(sb).toString();
    }

    @Override
    public String getFormat() {
        return this.messageTemplate;
    }

    @Override
    public Object[] getParameters() {
        return this.variables.values().toArray();
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }
}
