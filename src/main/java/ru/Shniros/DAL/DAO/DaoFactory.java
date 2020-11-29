package ru.Shniros.DAL.DAO;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DaoFactory {
    private static AccountDao accountDao;
    private static PersonDao personDao;
    private static TransactionDao transactionDao;
    private static TransactionCategoryDao transactionCategoryDao;
    private static DataSource dataSource;

    public static DataSource getDataSource(){
        final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
        final String username = "postgres";
        final String password = "12345";
        if(dataSource == null){
            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl(DB_URL);
            ds.setUsername(username);
            ds.setPassword(password);
            dataSource = ds;
        }
        return dataSource;
    }

    public static AccountDao getAccountDao(){
        if(accountDao == null){
            accountDao = new AccountDao(getDataSource());
        }
        return accountDao;
    }

    public static PersonDao getPersonDao(){
        if(personDao == null){
            personDao = new PersonDao(getDataSource());
        }
        return personDao;
    }
    public static TransactionDao getTransactionDao(){
        if(transactionDao == null){
            transactionDao = new TransactionDao(getDataSource());
        }
        return transactionDao;
    }
    public static TransactionCategoryDao getTransactionCategoryDao(){
        if(transactionCategoryDao == null){
            transactionCategoryDao = new TransactionCategoryDao(getDataSource());
        }
        return transactionCategoryDao;
    }
}
