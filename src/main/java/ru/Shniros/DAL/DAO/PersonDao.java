package ru.Shniros.DAL.DAO;

import ru.Shniros.DAL.domain.Person;

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
    public Person findById(Integer id) {
        String queru = "SELECT * FROM " + TABLE_NAME +
                " WHERE id = ?";

        try (Connection connection = dataSource.getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(queru);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            Person person = new Person();
            if(rs.next()){
                person.setId(rs.getInt("id"));
                person.setEmail(rs.getString("email"));
                person.setPassword(rs.getString("password"));
                person.setFirstName(rs.getString("first_name"));
                person.setLastName(rs.getString("last_name"));
                return person;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Person> findByAll() {
        final String query = "SELECT * FROM " + TABLE_NAME;
        try (Connection connection = dataSource.getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            List<Person> people = new ArrayList<Person>();
            while (rs.next()){
                people.add(new Person().setId(rs.getInt("id"))
                        .setEmail(rs.getString("email"))
                        .setPassword(rs.getString("password"))
                        .setFirstName(rs.getString("first_name"))
                        .setLastName(rs.getString("last_name")));
            }
            return people;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Person insert(Person person, Connection connection){
        final String insertSQL = "INSERT INTO " + TABLE_NAME +
                "(first_name, " +
                "last_name, " +
                "email, " +
                "password)" +
                " VALUES(?, ?, ?, ?)";
        try{
            PreparedStatement ps = connection.prepareStatement(insertSQL);
            ps.setString(1, person.getFirstName());
            ps.setString(2, person.getLastName());
            ps.setString(3, person.getEmail());
            ps.setString(4, person.getPassword());
            ps.executeUpdate();
            return person;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public Person findByEmail(String email) {
        final String query = "SELECT * FROM " + TABLE_NAME
                + " WHERE email = ?";
        try(Connection connection = dataSource.getConnection())
        {
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    @Override
    public Person update(Person person, Connection connection) throws SQLException {
        String query = "UPDATE " + TABLE_NAME +
                    " SET first_name = ?," +
                    "last_name = ?," +
                    "email = ?," +
                    "password = ?" +
                    " WHERE id = ?;";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,person.getFirstName());
        ps.setString(2,person.getLastName());
        ps.setString(3,person.getEmail());
        ps.setString(4,person.getPassword());
        ps.setInt(5,person.getId());
        ps.execute();
        return person;
    }

    @Override
    public boolean delete(Integer id, Connection connection){
        //TODO
        return false;
    }
}
