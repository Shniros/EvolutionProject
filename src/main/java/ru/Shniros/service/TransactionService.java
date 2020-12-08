package ru.Shniros.service;

import ru.Shniros.DAL.ConnectionService;
import ru.Shniros.DAL.DAO.AccountDao;
import ru.Shniros.DAL.DAO.TransactionDao;
import ru.Shniros.DAL.DAO.exception.CommonDaoException;
import ru.Shniros.converter.TransactionToTransactionDto;
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
    private final TransactionToTransactionDto converter;

    public TransactionService(AccountDao accountDao, TransactionDao transactionDao, DataSource dataSource, TransactionToTransactionDto converter) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
        this.dataSource = dataSource;
        this.converter = converter;
    }

    public void CreateTransaction(int categoryId, long fromAccountId, long toAccountId, BigDecimal sum) throws CommonServiceException {

        try (Connection connection = dataSource.getConnection();
             ConnectionService cs = new ConnectionService(connection,false))
        {
            Account fromAccount = accountDao.findById(fromAccountId);
            Account toAccount = accountDao.findById(toAccountId);

            if(fromAccount == null){
                throw new CommonServiceException("Can't find account id:" + fromAccountId);
            }
            if(toAccount == null){
                throw new CommonServiceException("Can't find account id:" + toAccountId);
            }
            BigDecimal fromAccountBalance = fromAccount.getBalance();
            if(fromAccountBalance.compareTo(sum) < 0){
                throw new CommonServiceException("Insufficient funds in the account:" + fromAccountBalance.add(sum.negate()));
            }
            fromAccount.setBalance(fromAccountBalance.add(sum.negate()));
            accountDao.update(fromAccount,connection);

            BigDecimal toAccountBalance = toAccount.getBalance();
            toAccount.setBalance(toAccountBalance.add(sum));
            accountDao.update(toAccount,connection);

            Transaction transaction = new Transaction();
            transaction.setFromAccountId(fromAccount.getId());
            transaction.setToAccountId(toAccount.getId());
            transaction.setSum(sum);
            transaction.setDate(new Date());
            transaction.setCategoryId(categoryId);
            transactionDao.insert(transaction,connection);

            cs.commit();
        } catch (CommonDaoException | SQLException ex) {
           throw new CommonServiceException("Cannot create transaction",ex);
        }
    }
}
