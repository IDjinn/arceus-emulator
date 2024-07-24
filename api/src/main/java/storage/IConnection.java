package storage;

public interface IConnection {
    java.sql.Connection get() throws Exception;

    IConnectionContext getConnectionContext();
}
