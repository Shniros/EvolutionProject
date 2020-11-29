package ru.Shniros.service;

import ru.Shniros.DAL.DAO.AccountDao;
import ru.Shniros.DAL.DAO.exception.CommonDaoException;
import ru.Shniros.domain.Account;
import ru.Shniros.domain.Person;
import ru.Shniros.service.exception.CommonServiceException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class AccountService {
    private final AccountDao accountDao;
    private final DataSource dataSource;

    public AccountService(AccountDao accountDao, DataSource dataSource) {
        this.accountDao = accountDao;
        this.dataSource = dataSource;
    }

    public Account CreateAccount(Account account, Person person) throws CommonServiceException {
        try (Connection connection = dataSource.getConnection()){
            if(accountDao.countAccountByPersonId(person.getId()) < 5){
                accountDao.insert(account, connection);
            }else{
                throw new CommonServiceException("user have 5 accounts");
            }
        }catch (CommonDaoException | SQLException ex){
            throw new CommonServiceException("Cannot create account",ex);
        }
        return account;
    }
}