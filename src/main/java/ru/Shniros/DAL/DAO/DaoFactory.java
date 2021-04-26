package ru.Shniros.DAL.DAO;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import ru.Shniros.DAL.DAO.exception.CommonDaoException;

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
        final String password = "qwe1";
        if(dataSource == null){
            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl(System.getProperty("jdbcURL",DB_URL));
            ds.setUsername(System.getProperty("jdbcUsername",username));
            ds.setPassword(System.getProperty("jdbcPassword"  ,password));
            dataSource = ds;
            initDataBase();
        }
        return dataSource;
    }

    private static void initDataBase(){
        try {
            DatabaseConnection connection = new JdbcConnection(dataSource.getConnection());
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
            database.setDefaultSchemaName("finance");
            Liquibase liquibase = new Liquibase(
                    "liquibase.xml",
                    new ClassLoaderResourceAccessor(),
                    database
            );
            liquibase.update(new Contexts());
        } catch (SQLException | LiquibaseException ex) {
           // throw new CommonDaoException("",ex);
            ex.printStackTrace();
        }
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
