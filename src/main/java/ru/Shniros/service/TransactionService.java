package ru.Shniros.service;

import ru.Shniros.DAL.DAO.AccountDao;
import ru.Shniros.DAL.DAO.TransactionDao;
import ru.Shniros.DAL.DAO.exception.CommonDaoException;
import ru.Shniros.domain.Account;
import ru.Shniros.domain.Transaction;
import ru.Shniros.service.exception.CommonServiceException;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class TransactionService {
    private final AccountDao accountDao;
    private final TransactionDao transactionDao;
    private final DataSource dataSource;

    public TransactionService(AccountDao accountDao, TransactionDao transactionDao, DataSource dataSource) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
        this.dataSource = dataSource;
    }

    public void CreateTransaction(Integer categoryId, Long fromAccountId, Long toAccountId, BigDecimal sum) throws CommonServiceException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            Account fromAccount = accountDao.findById(fromAccountId);
            Account toAccount = accountDao.findById(toAccountId);

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
            accountDao.update(fromAccount,connection);
            accountDao.update(toAccount,connection);
            Transaction transaction = new Transaction();
            transaction.setFromAccountId(fromAccount.getId());
            transaction.setToAccountId(toAccount.getId());
            transaction.setSum(sum);
            transaction.setDate(new Date());
            transaction.setCategoryId(categoryId);
            transactionDao.insert(transaction,connection);
            connection.commit();

        } catch (CommonDaoException | SQLException ex) {
            if(connection != null){
                try {
                     connection.rollback();
                    } catch (SQLException ignored) { }
            }
            throw new CommonServiceException("Cannot create transaction",ex);
        }finally {
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException ignored) {}
            }
        }

    }
}
