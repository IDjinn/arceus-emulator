package storage.data;

import java.sql.PreparedStatement;

public interface IConnectionStatementConsumer {
    void accept(final PreparedStatement statement) throws Exception;
}
