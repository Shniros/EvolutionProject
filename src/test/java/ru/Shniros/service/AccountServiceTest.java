package ru.Shniros.service;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import ru.Shniros.DAL.DAO.AccountDao;
import ru.Shniros.DAL.DAO.exception.CommonDaoException;

import ru.Shniros.domain.Account;
import ru.Shniros.service.exception.CommonServiceException;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceTest {
    private AccountService subj;
    private AccountDao accountDao;

    @BeforeEach
    void setUp(){
        accountDao = mock(AccountDao.class);

        subj = new AccountService(accountDao);
    }

    @Test
    void createAccount_amountAccountMoreMax() throws CommonServiceException, CommonDaoException {
        Account account = new Account();
        account.setPersonId(1);
        when(accountDao.countAccountByPersonId(account.getPersonId())).thenReturn(5);

        Account testAccount = subj.CreateAccount(account);
        assertNull(testAccount);
    }

    @Test
    void createAccount_ok() throws CommonServiceException, CommonDaoException {
        Account account = new Account();
        account.setPersonId(1);
        //account.setName("test");
        account.setId(1);
        Account dto = new Account();
        dto.setId(1);
       // dto.setName("test");
        when(accountDao.countAccountByPersonId(account.getPersonId())).thenReturn(1);
        when(accountDao.insert(account)).thenReturn(account);

        Account testAccount = subj.CreateAccount(account);

        assertEquals(account,testAccount);
    }
}