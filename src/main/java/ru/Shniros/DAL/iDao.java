package ru.Shniros.DAL;

import ru.Shniros.DAL.DAO.exception.CommonDaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface iDao <DOMAIN, ID> {
    DOMAIN findById(ID id) throws CommonDaoException;
    List<DOMAIN> findByAll() throws CommonDaoException;
    DOMAIN insert(DOMAIN domain, Connection connection) throws SQLException, CommonDaoException;
    DOMAIN update(DOMAIN domain, Connection connection) throws SQLException, CommonDaoException;
    boolean delete(ID id, Connection connection);
}