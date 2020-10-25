package ru.Shniros.DAL.DAO.Impl;

import com.sun.rowset.internal.InsertRow;
import ru.Shniros.DAL.DAO.iPersonDAO;
import ru.Shniros.jdbc.SingleConnectionManager;
import ru.Shniros.DAL.Person;

import java.sql.*;

public class PersonDAO implements iPersonDAO {
    private String TABLE_NAME = "finance.person";
    private static Connection connection;

    @Override
    public boolean addNewPerson(Person person){

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
                    " VALUES(?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(insertSQL);
            ps.setInt(1, person.getId());
            ps.setString(2, person.getFirstName());
            ps.setString(3, person.getLastName());
            ps.setString(4, person.getEmail());
            ps.setString(5, person.getPassword());
            ps.setInt(6, person.getAge());
            ps.setInt(7,person.getBalance());
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
                findPerson.setAge(rs.getInt("age"));
                findPerson.setBalance(rs.getInt("balance"));
                return findPerson;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
