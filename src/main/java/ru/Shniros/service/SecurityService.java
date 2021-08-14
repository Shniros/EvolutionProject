package ru.Shniros.service;

import ru.Shniros.DAL.DAO.PersonDao;
import ru.Shniros.DAL.DAO.exception.CommonDaoException;
import ru.Shniros.domain.Person;
import ru.Shniros.service.exception.CommonServiceException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class SecurityService {
    private PersonDao personDao;
    private DigestService digestService;

    public SecurityService() {
    }

    @Inject
    public SecurityService(PersonDao personDao,
                           DigestService digestService) {
        this.personDao = personDao;
        this.digestService = digestService;
    }

    public Person login(String email, String password) throws CommonServiceException {
        try {
            Person curPerson = personDao.findByEmail(email);
            if (curPerson != null) {
                if (digestService.getMd5(password).equals(curPerson.getPassword())) {
                    return curPerson;
                }
            }
        } catch (CommonDaoException daoEx) {
            throw new CommonServiceException("Cannot login person", daoEx);
        }
        return null;
    }

    public Person registration(Person person) throws CommonServiceException {
        try {
            if (personDao.findByEmail(person.getEmail()) == null) {
                return personDao.insert(person);
            }
        } catch (CommonDaoException ex) {
            throw new CommonServiceException("Cannot registration person", ex);
        }
        return null;
    }
}