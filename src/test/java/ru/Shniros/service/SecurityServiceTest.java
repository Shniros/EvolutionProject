package ru.Shniros.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.Shniros.DAL.DAO.PersonDao;
import ru.Shniros.DAL.DAO.exception.CommonDaoException;

import ru.Shniros.domain.Person;
import ru.Shniros.service.exception.CommonServiceException;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SecurityServiceTest {
    SecurityService subj;
    PersonDao personDao;
    DigestService digestService;

    @BeforeEach
    void setUp() {
        personDao = mock(PersonDao.class);
        digestService = mock(DigestService.class);

        subj = new SecurityService(personDao,digestService);
    }

    @Test
    void login_personNotFoundByEmail() throws CommonServiceException, CommonDaoException {
        when(personDao.findByEmail("jobshniros@gmail.com")).thenReturn(null);
        Person findPerson = subj.login("jobshniros@gmail.com","password");
        assertNull(findPerson);

    }
    @Test
    void login_personFoundButPasswordWrong() throws CommonDaoException, CommonServiceException {
        Person personService = new Person();
        personService.setPassword("some password");
        when(personDao.findByEmail("jobshniros@gmail.com")).thenReturn(personService);
        when(digestService.getMd5("password")).thenReturn("other password");
        Person findPerson = subj.login("jobshniros@gmail.com","password");
        assertNull(findPerson);
    }

    @Test
    void login_ok() throws CommonDaoException, CommonServiceException {
        Person  dto= new Person();
        dto.setId(1);
        Person person = new Person();
        person.setPassword("some password");
        when(personDao.findByEmail("jobshniros@gmail.com")).thenReturn(person);
        when(digestService.getMd5("password")).thenReturn("some password");
       // when(person).thenReturn(dto);

        Person testPerson = subj.login("jobshniros@gmail.com","password");
        assertEquals(person,testPerson);
    }

    @Test
    void registration_EmailExist() throws CommonDaoException, CommonServiceException {
        Person person = new Person();
        person.setEmail("jobshniros@gmail.com");
        person.setPassword("password");

        when(personDao.findByEmail(person.getEmail())).thenReturn(person);
        Person testPerson = subj.registration(person);
        assertNull(testPerson);
    }
    @Test
    void registration_ok() throws CommonDaoException, CommonServiceException {
        Person person = new Person();
        person.setEmail("jobshniros@gmail.com");
        person.setPassword("some password");
        when(personDao.findByEmail(person.getEmail())).thenReturn(null);
        when(personDao.insert(person)).thenReturn(person);

        Person testPerson = subj.registration(person);
        assertEquals(person,testPerson);
    }
}