package dataSource.Shniros.service;

import dataSource.Shniros.DBase.DAO.AccountDao;
import dataSource.Shniros.DBase.DAO.DaoFactory;
import dataSource.Shniros.DBase.DAO.TransactionDao;
import dataSource.Shniros.DBase.domain.Account;
import dataSource.Shniros.DBase.domain.Transaction;
import dataSource.Shniros.exception.CommonServiceException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class TransactionService {
    private final AccountDao accountDao;
    private final TransactionDao transactionDao;

    public TransactionService(AccountDao accountDao, TransactionDao transactionDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
    }

    public void CreateTransaction(Integer categoryId, Long fromAccountId, Long toAccountId, BigDecimal sum){
        Connection connection = null;
        try {
            connection = DaoFactory.getDataSource().getConnection();
            connection.setAutoCommit(false);

            Account fromAccount = accountDao.findById(fromAccountId);
            Account toAccount = accountDao.findById(toAccountId);

            if(fromAccount == null){
                throw new CommonServiceException(TransactionService.class.getName(),"Can't find account id:" + fromAccount.getId());
            }
            if(toAccount == null){
                throw new CommonServiceException(TransactionService.class.getName(),"Can't find account id:" + toAccount.getId());
            }
            BigDecimal fromAccountBalance = fromAccount.getBalance();
            if(fromAccountBalance.compareTo(sum) < 0){
                throw new CommonServiceException(TransactionService.class.getName(),
                        "Insufficient funds in the account:" + fromAccountBalance.add(sum.negate()));
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
