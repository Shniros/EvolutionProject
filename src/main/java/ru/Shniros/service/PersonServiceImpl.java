package ru.Shniros.service;

import org.springframework.util.DigestUtils;
import ru.Shniros.DAO.Impl.PersonDAO;
import ru.Shniros.DAO.domain.Person;
import ru.Shniros.DAO.jdbc.SingleConnectionManager;

import java.sql.SQLException;

public class PersonServiceImpl{
    Person curPerson;

    public Person login(String email, String password) {
        PersonDAO dao = new PersonDAO();
        curPerson = dao.findByEmail(email);
        if (curPerson != null) {
            if (DigestUtils.md5DigestAsHex(password.getBytes()).equals(
                    curPerson.getPassword())) {
                return curPerson;
            }
        }
        return null;
    }

    public boolean registration(Person person) {
        PersonDAO dao = new PersonDAO();
        if(dao.findByEmail(person.getEmail()) == null){
            try {
                dao.insert(person, SingleConnectionManager.getConnection());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return true;
        }else{

        }
        return false;
    }
}