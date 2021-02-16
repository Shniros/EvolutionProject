package ru.Shniros;

import ru.Shniros.DAL.DAO.DaoFactory;
import ru.Shniros.DAL.DAO.PersonDao;
import ru.Shniros.DAL.DAO.exception.CommonDaoException;
import ru.Shniros.domain.Account;
import ru.Shniros.domain.Person;
import ru.Shniros.service.ServiceFactory;
import ru.Shniros.service.TransactionService;

import java.math.BigDecimal;
import java.sql.SQLException;

public class Demo {
    public static void main(String[] args) throws CommonDaoException {
      //  TransactionService ts = ServiceFactory.getTransactionService();
        //ts.CreateTransaction(1,2L,3L, BigDecimal.valueOf(150))
       // System.out.println(DaoFactory.getTransactionDao().findByAll());

        System.out.println( DaoFactory.getPersonDao().findById(1));
        System.out.println(DaoFactory.getAccountDao().findById(1L));

    }
}
