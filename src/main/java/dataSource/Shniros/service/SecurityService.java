package dataSource.Shniros.service;

import dataSource.Shniros.DBase.DAO.DaoFactory;
import dataSource.Shniros.DBase.DAO.PersonDao;
import dataSource.Shniros.DBase.domain.Person;
import dataSource.Shniros.exception.CommonServiceException;

import java.sql.Connection;

public class SecurityService {
    private final PersonDao personDao;

    public SecurityService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public Person login(String email, String password){

        Person curPerson = personDao.findByEmail(email);
        try {
            if (curPerson != null) {
                if (DigestService.getHash(password).equals(
                        curPerson.getPassword())) {
                    return curPerson;
                } else {
                    throw new CommonServiceException(SecurityService.class.getName(),"Wrong password: " + password);
                }
            } else {
                throw new CommonServiceException(SecurityService.class.getName(),"Wrong email: " + email);
            }
        }catch (Exception ignored){}
        return null;
    }

    public Person registration(Person person){

        try (Connection connection =  DaoFactory.getDataSource().getConnection()){
            if(personDao.findByEmail(person.getEmail()) == null){
                return personDao.insert(person,connection);

            }else{
               throw new CommonServiceException(SecurityService.class.getName(),
                       "Email already exists: " + person.getEmail());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}