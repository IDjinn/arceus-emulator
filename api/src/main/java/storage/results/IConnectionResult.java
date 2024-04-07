package storage.results;

public interface IConnectionResult {
    String getString(String name) throws Exception;

    int getInt(String name) throws Exception;

    long getLong(String name) throws Exception;

    boolean getBoolean(String name) throws Exception;

    double getDouble(String name) throws Exception;

    boolean hasColumn(String name) throws Exception;

    boolean isNull(String name) throws Exception;
}
