package ru.Shniros.DAO;

import ru.Shniros.DAO.domain.Person;

public interface iPersonDAO {
    boolean addNewPerson(Person person);
    Person findByEmail(String email);
}
