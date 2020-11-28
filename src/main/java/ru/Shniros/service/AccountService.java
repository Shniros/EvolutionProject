package ru.Shniros.service;

import ru.Shniros.DBase.DAO.AccountDao;
import ru.Shniros.DBase.domain.Account;
import ru.Shniros.DBase.domain.Person;
import ru.Shniros.DBase.jdbc.SingleConnectionManager;
import ru.Shniros.exception.CommonServiceException;

import java.sql.SQLException;

public class AccountService {
    private AccountDao dao = new AccountDao();
    public Account CreateAccount(Account account, Person person){
        try {
            if(dao.countAccountByPersonId(person.getId()) < 5){
                dao.insert(account, SingleConnectionManager.getConnection());
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
