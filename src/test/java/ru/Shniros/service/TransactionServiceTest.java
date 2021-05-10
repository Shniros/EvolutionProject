package ru.Shniros.service;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.Shniros.DAL.DAO.AccountDao;
import ru.Shniros.DAL.DAO.DaoFactory;
import ru.Shniros.DAL.DAO.TransactionDao;
import ru.Shniros.DAL.DAO.exception.CommonDaoException;
import ru.Shniros.service.converter.TransactionToTransactionDto;
import ru.Shniros.domain.Account;
import ru.Shniros.domain.Person;
import ru.Shniros.domain.TransactionCategory;
import ru.Shniros.service.exception.CommonServiceException;

import javax.sql.DataSource;

import java.math.BigDecimal;



class TransactionServiceTest {
    TransactionService subj;
    AccountDao accountDao;
    TransactionDao transactionDao;
    DataSource dataSource;
    TransactionToTransactionDto converter;
    TransactionCategory tg;
    Account toAccount;
    Account fromAccount;

    @BeforeEach
    void setUp() throws CommonDaoException {
        System.setProperty("jdbcURL","jdbc:h2:mem:testdb;" +
                "MODE=PostgreSQL;" +
                "INIT=CREATE SCHEMA IF NOT EXISTS finance\\;" +
                "SET SCHEMA finance");
        System.setProperty("jdbcUsername","sa");
        System.setProperty("jdbcPassword"  ,"");
        Person person = new Person().setPassword("test").setEmail("test").setId(1);
        tg = new TransactionCategory().setId(1).setName("testCategory");
        fromAccount = new Account()
                .setId(1)
                .setPersonId(1)
                .setBalance(BigDecimal.valueOf(100))
                .setName("fromAccount");
        toAccount = new Account()
                .setId(2)
                .setPersonId(1)
                .setBalance(BigDecimal.valueOf(0))
                .setName("toAccount");
        DaoFactory.getPersonDao().insert(person);
        DaoFactory.getTransactionCategoryDao().insert(tg);
        DaoFactory.getAccountDao().insert(fromAccount);
        DaoFactory.getAccountDao().insert(toAccount);
    }
    //TODO fix text
    @Test
    void createTransaction_ok() throws CommonServiceException, CommonDaoException {
        subj = ServiceFactory.getTransactionService();
        subj.CreateTransaction(tg.getId(),fromAccount.getId(),toAccount.getId(),BigDecimal.valueOf(20));

        Assert.assertEquals(DaoFactory.getAccountDao().findById(fromAccount.getId()).getBalance(),BigDecimal.valueOf(80));
        Assert.assertEquals(DaoFactory.getAccountDao().findById(toAccount.getId()).getBalance(),BigDecimal.valueOf(20));
    }
    /*@Test
    void createTransaction_notFoundFromAccount() throws CommonDaoException{
    *//*    when(accountDao.findById(fromAccountId)).thenReturn(null).thenThrow(new CommonServiceException());
          Throwable ex = Assert.assertThrows(CommonServiceException.class,() -> {
            subj.CreateTransaction(categoryId,fromAccountId,toAccountId,sum);
        });
        willThrow(new CommonServiceException());
        doThrow(new CommonServiceException()).when(accountDao.findById(fromAccountId));
        when(accountDao.findById(fromAccountId)).thenReturn(null);
        when(accountDao.findById(fromAccountId)).thenThrow(new CommonServiceException());*//*
        int categoryId = 1;
        long fromAccountId = 1;
        long toAccountId = 2;
        BigDecimal sum  = BigDecimal.valueOf(1000);

        when(accountDao.findById(fromAccountId)).thenReturn(null);
        Throwable ex = Assert.assertThrows(CommonServiceException.class,() -> {
            subj.CreateTransaction(categoryId,fromAccountId,toAccountId,sum);
        });
        assertNotNull(ex.getMessage());
    }*/
}