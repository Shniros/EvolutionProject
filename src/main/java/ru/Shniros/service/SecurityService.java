package ru.Shniros.service;

import ru.Shniros.DAL.DAO.PersonDao;
import ru.Shniros.DAL.DAO.exception.CommonDaoException;
import ru.Shniros.converter.PersonToPersonDto;
import ru.Shniros.domain.Person;
import ru.Shniros.service.dto.PersonDto;
import ru.Shniros.service.exception.CommonServiceException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SecurityService {
    private final PersonDao personDao;
    private final  DigestService digestService;
    private final DataSource dataSource;
    public SecurityService(PersonDao personDao, DigestService digestService, DataSource dataSource) {
        this.personDao = personDao;
        this.digestService = digestService;
        this.dataSource = dataSource;
    }

    public PersonDto login(String email, String password) throws CommonServiceException {
        try {
            Person curPerson = personDao.findByEmail(email);
            if (curPerson != null) {
                if (digestService.getHash(password).equals(
                        curPerson.getPassword())) {
                    return new PersonToPersonDto().convert(curPerson);
                } else {
                    throw new CommonServiceException("Wrong password");
                }
            } else {
                throw new CommonServiceException("Wrong email");
            }
        }catch (CommonDaoException daoEx){
            throw new CommonServiceException("Cannot login person",daoEx);
        }
    }

    public PersonDto registration(Person person) throws CommonServiceException {
        try (Connection connection =  dataSource.getConnection()){
            if(personDao.findByEmail(person.getEmail()) == null){
                personDao.insert(person,connection);
            }else{
               throw new CommonServiceException("Email already exists");
            }
        } catch (CommonDaoException | SQLException ex) {
            throw new CommonServiceException("Cannot registration person",ex);
        }
        return new PersonToPersonDto().convert(person);
    }
}