package ru.Shniros.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import ru.Shniros.DAL.DAO.AccountDao;
import ru.Shniros.DAL.DAO.exception.CommonDaoException;
import ru.Shniros.converter.AccountToAccountDto;
import ru.Shniros.domain.Account;
import ru.Shniros.service.dto.AccountDto;
import ru.Shniros.service.exception.CommonServiceException;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceTest {
    private AccountService subj;
    private AccountDao accountDao;
    private DataSource dataSource;
    private AccountToAccountDto converter;

    @BeforeEach
    void setUp(){
        accountDao = mock(AccountDao.class);
        dataSource = mock(DataSource.class);
        converter = mock(AccountToAccountDto.class);

        subj = new AccountService(accountDao,dataSource,converter);
    }

    @Test
    void createAccount_amountAccountMoreMax() throws CommonServiceException, CommonDaoException {
        Account account = new Account();
        account.setPersonId(1);
        when(accountDao.countAccountByPersonId(account.getPersonId())).thenReturn(5);

        AccountDto testDto = subj.CreateAccount(account);
        assertNull(testDto);
    }

    @Test
    void createAccount_ok() throws CommonServiceException, CommonDaoException {
        Account account = new Account();
        account.setPersonId(1);
        //account.setName("test");
        account.setId(1);
        AccountDto dto = new AccountDto();
        dto.setId(1);
       // dto.setName("test");
        when(accountDao.countAccountByPersonId(account.getPersonId())).thenReturn(1);
        when(accountDao.insert(account)).thenReturn(account);
        when(converter.convert(account)).thenReturn(dto);

        AccountDto testDto = subj.CreateAccount(account);

        assertEquals(dto,testDto);
    }
}