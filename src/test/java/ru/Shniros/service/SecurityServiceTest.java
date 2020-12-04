package ru.Shniros.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.Shniros.DAL.DAO.PersonDao;
import ru.Shniros.DAL.DAO.exception.CommonDaoException;
import ru.Shniros.converter.PersonToPersonDto;
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
    PersonToPersonDto converter;

    @BeforeEach
    void setUp() {
        personDao = mock(PersonDao.class);
        digestService = mock(DigestService.class);
        dataSource = mock(DataSource.class);
        converter = mock(PersonToPersonDto.class);
        subj = new SecurityService(personDao,digestService,dataSource, converter);
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
        when(digestService.getMd5("password")).thenReturn("other password");
        PersonDto dto = subj.login("jobshniros@gmail.com","password");
        assertNull(dto);
    }

    @Test
    void login_ok() throws CommonDaoException, CommonServiceException {
        PersonDto dto = new PersonDto();
        dto.setId(1);
        Person person = new Person();
        person.setPassword("some password");
        when(personDao.findByEmail("jobshniros@gmail.com")).thenReturn(person);
        when(digestService.getMd5("password")).thenReturn("some password");
        when(converter.convert(person)).thenReturn(dto);

        PersonDto testDto = subj.login("jobshniros@gmail.com","password");
        assertEquals(dto,testDto);
    }

    @Test
    void registration_EmailExist() throws CommonDaoException, CommonServiceException {
        Person person = new Person();
        person.setEmail("jobshniros@gmail.com");
        person.setPassword("password");
        when(personDao.findByEmail(person.getEmail())).thenReturn(null);
        PersonDto testDto = subj.registration(person);
        assertNull(testDto);
    }
    @Test
    void registration_ok() throws CommonDaoException, CommonServiceException {
        PersonDto dto = new PersonDto();
        dto.setEmail("jobshniros@gmail.com");
        Person person = new Person();
        person.setEmail("jobshniros@gmail.com");
        person.setPassword("some password");
        when(personDao.findByEmail(person.getEmail())).thenReturn(null);
        when(converter.convert(person)).thenReturn(dto);

        PersonDto testDto = subj.registration(person);
        assertEquals(dto,testDto);
    }
}