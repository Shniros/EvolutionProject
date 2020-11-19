package ru.Shniros.DAO;

import ru.Shniros.DAO.domain.Person;

public interface iPersonDAO <DOMAIN, EMAIL>{
    boolean insertPerson(DOMAIN domain);
    DOMAIN findByEmail(EMAIL email);
    boolean updatePerson(DOMAIN domain);
    boolean deletePerson(DOMAIN domain);
}
