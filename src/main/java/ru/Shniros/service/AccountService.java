package ru.Shniros.service;

import ru.Shniros.DAL.DAO.AccountDao;
import ru.Shniros.DAL.DAO.exception.CommonDaoException;
import ru.Shniros.domain.Account;
import ru.Shniros.service.exception.CommonServiceException;

import javax.sql.DataSource;



public class AccountService {
    private final Integer MAX_ACCOUNT_AMOUNT = 5;
    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Account CreateAccount(Account account) throws CommonServiceException {
        try{
            if(accountDao.countAccountByPersonId(account.getPersonId()) < MAX_ACCOUNT_AMOUNT)
                return accountDao.insert(account);

        }catch (CommonDaoException ex){
            throw new CommonServiceException("Cannot create account",ex);
        }
        return null;
    }
}