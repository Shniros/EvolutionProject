package ru.Shniros.service;

import ru.Shniros.DAO.domain.Person;

public interface iPersonService {
    Person login(String email, String password) ;
    boolean registration(Person person);
}
