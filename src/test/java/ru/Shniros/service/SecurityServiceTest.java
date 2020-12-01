package ru.Shniros.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.Shniros.DAL.DAO.PersonDao;
import ru.Shniros.DAL.DAO.exception.CommonDaoException;
import ru.Shniros.domain.Person;
import ru.Shniros.service.dto.PersonDto;
import ru.Shniros.service.exception.CommonServiceException;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SecurityServiceTest {
    SecurityService subj;
    PersonDao personDao;
    DigestService digestService;
    DataSource dataSource;

    @BeforeEach
    void setUp() {
        personDao = mock(PersonDao.class);
        digestService = mock(DigestService.class);
        dataSource = mock(DataSource.class);
        subj = new SecurityService(personDao,digestService,dataSource);
    }

    @Test
    void login_personNotFoundByEmail() throws CommonServiceException, CommonDaoException {
        when(personDao.findByEmail("jobshniros@gmail.com")).thenReturn(null);
        PersonDto dto = subj.login("jobshniros@gmail.com","password");
        assertNull(dto);

    }
    @Test
    void login_personFoundButPasswordWrong() throws CommonDaoException, CommonServiceException {
        Person personService = new Person();
        personService.setPassword("some password");
        when(personDao.findByEmail("jobshniros@gmail.com")).thenReturn(personService);
        when(digestService.getHash("password")).thenReturn("other password");
        PersonDto dto = subj.login("jobshniros@gmail.com","password");
        assertNull(dto);
    }

    @Test
    void login_ok() throws CommonDaoException, CommonServiceException {
        Person personService = new Person();
        personService.setId(1);
        personService.setPassword("some_password");
        when(personDao.findByEmail("jobshniros@gmail.com")).thenReturn(personService);
        when(digestService.getHash("password")).thenReturn("some_password");

        PersonDto dto = subj.login("jobshniros@gmail.com","password");
        assertEquals(dto,personService);
    }
}