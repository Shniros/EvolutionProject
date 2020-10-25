package ru.Shniros.DAL.DAO;

import ru.Shniros.DAL.Person;

public interface iPersonDAO {
    boolean addNewPerson(Person person);
    Person findByEmail(String email);
}
