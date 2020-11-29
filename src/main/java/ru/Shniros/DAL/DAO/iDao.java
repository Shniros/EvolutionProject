package ru.Shniros.DAL.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface iDao <DOMAIN, ID> {
    DOMAIN findById(ID id);
    List<DOMAIN> findByAll();
    DOMAIN insert(DOMAIN domain, Connection connection) throws SQLException;
    DOMAIN update(DOMAIN domain, Connection connection) throws SQLException;
    boolean delete(ID id, Connection connection);
}