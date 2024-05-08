package habbo.variables;

import org.apache.logging.log4j.message.MessageFactory;

import java.util.List;

public interface IVariableMessageFactory extends MessageFactory {
    String format(final String message);

    String format(final String message, final List<IVariable> variables);
}