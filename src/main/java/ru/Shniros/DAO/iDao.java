package ru.Shniros.DAO;

import java.sql.Connection;
import java.util.List;

public interface iDao <DOMAIN, ID> {
    DOMAIN findById(ID id);
    List<DOMAIN> findByAll();
    DOMAIN insert(DOMAIN domain, Connection connection);
    DOMAIN update(DOMAIN domain, Connection connection);
    boolean delete(ID id, Connection connection);
}