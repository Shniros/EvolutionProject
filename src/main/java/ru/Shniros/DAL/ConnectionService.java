package ru.Shniros.DAL;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionService implements AutoCloseable{
    private final Connection connection;
    private boolean originalAutoCommit;
    private boolean committed;

    public ConnectionService(Connection connection,boolean autoCommit) throws SQLException {
        this.connection = connection;
        originalAutoCommit = connection.getAutoCommit();
        connection.setAutoCommit(autoCommit);
    }
    public void commit() throws SQLException {
        connection.commit();
        committed = true;
    }
    @Override
    public void close() throws SQLException {
        if(!committed){
            connection.rollback();
        }
        connection.setAutoCommit(originalAutoCommit);
    }
}
