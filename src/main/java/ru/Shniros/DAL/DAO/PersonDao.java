package ru.Shniros.DAL.DAO;

import ru.Shniros.DAL.DAO.exception.CommonDaoException;
import ru.Shniros.DAL.iDao;
import ru.Shniros.domain.Person;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDao implements iDao<Person, Integer> {
    private String TABLE_NAME = "finance.person";
    private final DataSource dataSource;

    public PersonDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Person findById(Integer id) throws CommonDaoException {
        Person person = new Person();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query))
        {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                person.setId(rs.getInt("id"));
                person.setEmail(rs.getString("email"));
                person.setPassword(rs.getString("password"));
                person.setFirstName(rs.getString("first_name"));
                person.setLastName(rs.getString("last_name"));
            }
        } catch (SQLException ex) {
            throw new CommonDaoException("Cannot find person by id",ex);
        }
        return person;
    }

    @Override
    public List<Person> findByAll() throws CommonDaoException {
        List<Person> people = new ArrayList<Person>();
        final String query = "SELECT * FROM " + TABLE_NAME;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query))
        {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                people.add(new Person().setId(rs.getInt("id"))
                        .setEmail(rs.getString("email"))
                        .setPassword(rs.getString("password"))
                        .setFirstName(rs.getString("first_name"))
                        .setLastName(rs.getString("last_name")));
            }
        } catch (SQLException ex) {
            throw new CommonDaoException("Cannot find all people",ex);
        }
        return people;
    }

    @Override
    public Person insert(Person person) throws CommonDaoException {
        final String insertSQL = "INSERT INTO " + TABLE_NAME +
                "(first_name, " +
                "last_name, " +
                "email, " +
                "password)" +
                " VALUES(?, ?, ?, ?)";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(insertSQL))
        {
            ps.setString(1, person.getFirstName());
            ps.setString(2, person.getLastName());
            ps.setString(3, person.getEmail());
            ps.setString(4, person.getPassword());
            ps.executeUpdate();
        } catch (SQLException ex) {
           throw new CommonDaoException("Cannot insert person",ex);
        }
        return person;
    }
    public Person findByEmail(String email) throws CommonDaoException {
        Person findPerson = null;
        final String query = "SELECT * FROM " + TABLE_NAME
                + " WHERE email = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(query))
        {
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                findPerson = new Person();
                findPerson.setId(rs.getInt("id"));
                findPerson.setFirstName(rs.getString("first_name"));
                findPerson.setLastName(rs.getString("last_name"));
                findPerson.setEmail(rs.getString("email"));
                findPerson.setPassword(rs.getString("password"));
            }
        } catch (SQLException ex) {
            throw new CommonDaoException("Cannot find person by e-mail",ex);
        }
        return findPerson;
    }
    @Override
    public Person update(Person person) throws CommonDaoException {
        String query = "UPDATE " + TABLE_NAME +
                    " SET first_name = ?," +
                    "last_name = ?," +
                    "email = ?," +
                    "password = ?" +
                    " WHERE id = ?;";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(query))
        {
            ps.setString(1, person.getFirstName());
            ps.setString(2, person.getLastName());
            ps.setString(3, person.getEmail());
            ps.setString(4, person.getPassword());
            ps.setInt(5, person.getId());
            ps.execute();
        }catch (SQLException ex){
            throw new CommonDaoException("Cannot update person data",ex);
        }
        return person;
    }

    @Override
    public boolean delete(Integer id, Connection connection){
        //TODO
        return false;
    }
}