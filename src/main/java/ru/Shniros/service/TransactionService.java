package ru.Shniros.service;

import ru.Shniros.DAO.Impl.AccountDAO;
import ru.Shniros.DAO.Impl.PersonDAO;
import ru.Shniros.DAO.Impl.TransactionDAO;
import ru.Shniros.DAO.domain.Account;
import ru.Shniros.DAO.domain.Person;
import ru.Shniros.DAO.domain.Transaction;
import ru.Shniros.DAO.jdbc.SingleConnectionManager;

import java.beans.Expression;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class TransactionService {
    public void CreateTransaction(Long fromAccountId, Long toAccountId, BigDecimal sum){
        Connection connection = null;
        try {
            connection = SingleConnectionManager.getConnection();
            connection.setAutoCommit(false);
            AccountDAO accountDAO = new AccountDAO();
            Account fromAccount = accountDAO.findById(fromAccountId);
            Account toAccount = accountDAO.findById(toAccountId);

            if(fromAccount == null){
                throw new Exception();
            }
            if(toAccount == null){
                throw new Exception();
            }
            BigDecimal fromAccountBalance = fromAccount.getBalance();
            if(fromAccountBalance.compareTo(sum) < 0){
                throw new Exception();
            }
            fromAccount.setBalance(fromAccountBalance.add(sum.negate()));
            toAccount.setBalance(toAccount.getBalance().add(sum));
            accountDAO.update(fromAccount,connection);
            accountDAO.update(toAccount,connection);
            Transaction transaction = new Transaction();
            transaction.setFromAccountId(fromAccount.getId());
            transaction.setToAccountId(toAccount.getId());
            transaction.setSum(sum);
            transaction.setDate(new Date());
            new TransactionDAO().insert(transaction,connection);
            connection.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            if(connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ignored) {}
            }
        }finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignored) {}
            }
        }

    }
}
