package storage.repositories;

import storage.data.IConnectionStatementConsumer;
import storage.results.IConnectionBooleanResultConsumer;
import storage.results.IConnectionResultConsumer;

public interface IConnectionRepository {
    void select(String query, IConnectionResultConsumer resultConsumer, Object... parameters);

    void updateBatch(String query, IConnectionStatementConsumer statementConsumer, IConnectionResultConsumer resultConsumer);

    void update(String query, IConnectionBooleanResultConsumer consumer, Object... parameters);

    void insertBatch(String query, IConnectionStatementConsumer statementConsumer, IConnectionResultConsumer resultConsumer);

    void insert(String query, IConnectionResultConsumer consumer, Object... parameters);

    void delete(String query, IConnectionBooleanResultConsumer consumer, Object... parameters);
}
