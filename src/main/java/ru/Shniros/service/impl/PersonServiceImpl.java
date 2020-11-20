package ru.Shniros.service.impl;

import org.springframework.util.DigestUtils;
import ru.Shniros.DAO.Impl.PersonDAO;
import ru.Shniros.DAO.domain.Person;
import ru.Shniros.service.iPersonService;

public class PersonServiceImpl implements iPersonService {
    Person curPerson;

    @Override
    public Person login(String email, String password) {
        PersonDAO dao = new PersonDAO();
        curPerson = dao.findByEmail(email);
        if (curPerson != null) {
            if (DigestUtils.md5DigestAsHex(password.getBytes()).equals(
                    curPerson.getPassword())) {
                System.out.println("Hello, " + curPerson.getFirstName());
                return curPerson;
            }
        }
        System.out.println("wrong login or password..");
        return null;
    }
    @Override
    public boolean registration(Person person) {
        PersonDAO dao = new PersonDAO();
        if(dao.findByEmail(person.getEmail()) == null){
            dao.insert(person);;
            System.out.println("great!!");
            return true;
        }else{
            System.out.println("Error..");
        }
        return false;
    }
}