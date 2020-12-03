package ru.Shniros.service;

import ru.Shniros.DAL.DAO.AccountDao;
import ru.Shniros.DAL.DAO.exception.CommonDaoException;
import ru.Shniros.converter.AccountToAccountDto;
import ru.Shniros.domain.Account;
import ru.Shniros.domain.Person;
import ru.Shniros.service.dto.AccountDto;
import ru.Shniros.service.exception.CommonServiceException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class AccountService {
    private final Integer MAX_ACCOUNT_AMOUNT = 5;
    private final AccountDao accountDao;
    private final DataSource dataSource;
    private final AccountToAccountDto converter;

    public AccountService(AccountDao accountDao, DataSource dataSource, AccountToAccountDto converter) {
        this.accountDao = accountDao;
        this.dataSource = dataSource;
        this.converter = converter;
    }

    public AccountDto CreateAccount(Account account, Integer personId) throws CommonServiceException {
        try (Connection connection = dataSource.getConnection()){
            if(accountDao.countAccountByPersonId(personId) < MAX_ACCOUNT_AMOUNT){
                return converter.convert(accountDao.insert(account, connection));
            }
        }catch (CommonDaoException | SQLException ex){
            throw new CommonServiceException("Cannot create account",ex);
        }
        return null;
    }
}