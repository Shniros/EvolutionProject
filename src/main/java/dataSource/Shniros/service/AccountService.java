package dataSource.Shniros.service;

import dataSource.Shniros.DBase.DAO.AccountDao;
import dataSource.Shniros.DBase.DAO.DaoFactory;
import dataSource.Shniros.DBase.domain.Account;
import dataSource.Shniros.DBase.domain.Person;
import dataSource.Shniros.exception.CommonServiceException;

import java.sql.SQLException;

public class AccountService {
    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Account CreateAccount(Account account, Person person){
        try {
            if(accountDao.countAccountByPersonId(person.getId()) < 5){
                accountDao.insert(account, DaoFactory.getDataSource().getConnection());
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
