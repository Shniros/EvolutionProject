package ru.Shniros.service;

import ru.Shniros.DBase.DAO.AccountDao;
import ru.Shniros.DBase.domain.Account;
import ru.Shniros.DBase.domain.Person;
import ru.Shniros.exception.CommonServiceException;

import javax.sql.DataSource;
import java.sql.SQLException;

public class AccountService {
    private final AccountDao accountDao;
    private final DataSource dataSource;

    public AccountService(AccountDao accountDao, DataSource dataSource) {
        this.accountDao = accountDao;
        this.dataSource = dataSource;
    }

    public Account CreateAccount(Account account, Person person){
        try {
            if(accountDao.countAccountByPersonId(person.getId()) < 5){
                accountDao.insert(account, dataSource.getConnection());
                return account;
            }else{
                throw new CommonServiceException(AccountService.class.getName(),"user have 5 accounts");
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }catch (CommonServiceException cse){
            cse.printStackTrace();
        }
        return null;
    }
}
