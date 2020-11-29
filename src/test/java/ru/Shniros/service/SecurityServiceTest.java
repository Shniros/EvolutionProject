package ru.Shniros.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.Shniros.DBase.DAO.PersonDao;
import ru.Shniros.DBase.domain.Person;

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
    void login_personNotFoundByEmail() {
        when(personDao.findByEmail("jobshniros@gmail.com")).thenReturn(null);
        Person person = subj.login("jobshniros@gmail.com","password");
        assertNull(person);

    }
    @Test
    void login_personFoundButPasswordWrong() {
        Person personService = new Person();
        personService.setPassword("some password");
        when(personDao.findByEmail("jobshniros@gmail.com")).thenReturn(personService);
        when(digestService.getHash("password")).thenReturn("other password");
        Person person = subj.login("jobshniros@gmail.com","password");
        assertNull(person);
    }

    @Test
    void login_ok() {
        Person personService = new Person();
        personService.setId(1);
        personService.setPassword("some_password");
        when(personDao.findByEmail("jobshniros@gmail.com")).thenReturn(personService);
        when(digestService.getHash("password")).thenReturn("some_password");

        Person person = subj.login("jobshniros@gmail.com","password");
        assertEquals(person,personService);
    }
}