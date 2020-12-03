package ru.Shniros.service;

import org.junit.jupiter.api.BeforeEach;
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
    AccountService subj;
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
    void createAccount_amountAccountMoreMax() throws CommonServiceException {
        Account account = new Account();
        account.setPersonId(1);
        Integer person_id = account.getPersonId();
        when(accountDao.countAccountByPersonId(person_id)).thenReturn(1);

        AccountDto testDto = subj.CreateAccount(account,person_id);
        assertNull(testDto);
    }

    @Test
    void createAccount_ok() throws CommonServiceException, SQLException, CommonDaoException {
        Account account = new Account();
        account.setPersonId(1);
        account.setId(1);
        Integer person_id = account.getPersonId();
        AccountDto dto = new AccountDto();
        dto.setId(1);
        when(accountDao.countAccountByPersonId(person_id)).thenReturn(1);
        when(accountDao.insert(account, dataSource.getConnection())).thenReturn(account);
        when(converter.convert(account)).thenReturn(dto);

        AccountDto testDto = subj.CreateAccount(account,person_id);
        assertEquals(dto,testDto);
    }
}