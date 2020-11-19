package ru.Shniros.DAO.Impl;

import ru.Shniros.DAO.iPersonDAO;
import ru.Shniros.DAO.jdbc.SingleConnectionManager;
import ru.Shniros.DAO.domain.Person;

import java.sql.*;

public class PersonDAO implements iPersonDAO {
    private String TABLE_NAME = "finance.person";
    private static Connection connection;

    @Override
    public boolean addPerson(Person person){

        try{
            connection = SingleConnectionManager.getConnection();
            String insertSQL = "INSERT INTO " + TABLE_NAME +
                    "(id, " +
                    "first_name, " +
                    "last_name, " +
                    "email, " +
                    "password, " +
                    "age, " +
                    "balance)" +
                    " VALUES(?, ?, ?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(insertSQL);
            ps.setInt(1, person.getId());
            ps.setString(2, person.getFirstName());
            ps.setString(3, person.getLastName());
            ps.setString(4, person.getEmail());
            ps.setString(5, person.getPassword());
            ps.executeUpdate();
            connection.close();
            return true;

        }catch (SQLException throwables){
            throwables.getStackTrace();
        }
        return false;
    }
    @Override
    public Person findByEmail(String email) {
        try {
            connection = SingleConnectionManager.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME
                    + " WHERE email = ?;";
            PreparedStatement p_stmt = connection.prepareStatement(query);
            p_stmt.setString(1,email);
            ResultSet rs = p_stmt.executeQuery();
            if(rs.next()){
                Person findPerson = new Person();
                findPerson.setId(rs.getInt("id"));
                findPerson.setFirstName(rs.getString("first_name"));
                findPerson.setLastName(rs.getString("last_name"));
                findPerson.setEmail(rs.getString("email"));
                findPerson.setPassword(rs.getString("password"));
                return findPerson;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
