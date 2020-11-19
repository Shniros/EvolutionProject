package ru.Shniros.DAO.jdbc;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingleConnectionManager {
    private static final String DATABASE_SCHEMA_NAME = "finance";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
           // + DATABASE_SCHEMA_NAME;
           // + "?serverTimezone=UTC";
    private static final String username = "postgres";
    private static final String password = "12345";
    private static DataSource dataSource;
    public static Connection getConnection() throws SQLException {
        if(dataSource == null){
            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl(DB_URL);
            ds.setUsername(username);
            ds.setPassword(password);
            dataSource = ds;
        }
        return dataSource.getConnection();
    }
}
