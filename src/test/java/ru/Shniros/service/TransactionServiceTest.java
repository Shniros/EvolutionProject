package ru.Shniros.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.Shniros.DAL.DAO.AccountDao;
import ru.Shniros.DAL.DAO.TransactionDao;
import ru.Shniros.converter.TransactionToTransactionDto;
import ru.Shniros.service.exception.CommonServiceException;

import javax.sql.DataSource;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;


class TransactionServiceTest {
    TransactionService subj;
    AccountDao accountDao;
    TransactionDao transactionDao;
    DataSource dataSource;
    TransactionToTransactionDto converter;


    @BeforeEach
    void setUp() {
        accountDao = mock(AccountDao.class);
        transactionDao = mock(TransactionDao.class);
        dataSource = mock(DataSource.class);
        converter = mock(TransactionToTransactionDto.class);

        subj = new TransactionService(accountDao,
                transactionDao,
                dataSource,
                converter);
        System.out.println(subj.toString());
    }
    @Test
    void createTransaction_test() throws CommonServiceException {
        int categoryId = 1;
        long fromAccountId = 1;
        long toAccountId = 2;
        BigDecimal sum  = BigDecimal.valueOf(1000);
        subj.CreateTransaction(1,1,2,BigDecimal.valueOf(1000));
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