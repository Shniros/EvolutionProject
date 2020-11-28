package ru.Shniros;

import ru.Shniros.DBase.DAO.DaoFactory;
import ru.Shniros.service.ServiceFactory;
import ru.Shniros.service.TransactionService;

import java.math.BigDecimal;
import java.sql.SQLException;

public class Demo {
    public static void main(String[] args) throws SQLException {
        TransactionService ts = ServiceFactory.getTransactionService();
        //ts.CreateTransaction(1,2L,3L, BigDecimal.valueOf(150))
        System.out.println(DaoFactory.getTransactionDao().findByAll());
    }
}
