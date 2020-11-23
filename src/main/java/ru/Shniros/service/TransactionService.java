package ru.Shniros.service;

import ru.Shniros.DAO.Impl.AccountDao;
import ru.Shniros.DAO.Impl.TransactionDao;
import ru.Shniros.DAO.domain.Account;
import ru.Shniros.DAO.domain.Transaction;
import ru.Shniros.DAO.domain.TransactionCategory;
import ru.Shniros.DAO.jdbc.SingleConnectionManager;
import ru.Shniros.exception.CommonServiceException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class TransactionService {
    public void CreateTransaction(TransactionCategory category, Long fromAccountId, Long toAccountId, BigDecimal sum){
        Connection connection = null;
        try {
            connection = SingleConnectionManager.getConnection();
            connection.setAutoCommit(false);
            AccountDao accountDAO = new AccountDao();
            Account fromAccount = accountDAO.findById(fromAccountId);
            Account toAccount = accountDAO.findById(toAccountId);

            if(fromAccount == null){
                throw new CommonServiceException("Can't find account id:" + fromAccount.getId());
            }
            if(toAccount == null){
                throw new CommonServiceException("Can't find account id:" + toAccount.getId());
            }
            BigDecimal fromAccountBalance = fromAccount.getBalance();
            if(fromAccountBalance.compareTo(sum) < 0){
                throw new CommonServiceException("Insufficient funds in the account:" + fromAccountBalance.add(sum.negate()));
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
            transaction.setCategoryId(category.getId());
            new TransactionDao().insert(transaction,connection);
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
