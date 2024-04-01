package storage;

public interface IConnection {
    public java.sql.Connection get() throws Exception;

    public IConnectionContext getConnectionContext();
}
