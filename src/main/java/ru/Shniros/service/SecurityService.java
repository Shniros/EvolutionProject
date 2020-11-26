package ru.Shniros.service;

import org.springframework.util.DigestUtils;
import ru.Shniros.DBase.DAO.PersonDao;
import ru.Shniros.DBase.domain.Person;
import ru.Shniros.DBase.jdbc.SingleConnectionManager;
import ru.Shniros.exception.CommonServiceException;

import java.sql.SQLException;
import java.util.logging.Logger;

public class SecurityService {
    Person curPerson;

    public Person login(String email, String password){
        PersonDao dao = new PersonDao();
        curPerson = dao.findByEmail(email);
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
        PersonDao dao = new PersonDao();
        try {
            if(dao.findByEmail(person.getEmail()) == null){
                return dao.insert(person, SingleConnectionManager.getConnection());

            }else{
               throw new CommonServiceException(SecurityService.class.getName(),
                       "Email already exists: " + person.getEmail());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                throw new CommonServiceException(SecurityService.class.getName(), "SQL: " + ex.toString(), ex);
            }catch (Exception ignored){}
        }
        return null;
    }
}