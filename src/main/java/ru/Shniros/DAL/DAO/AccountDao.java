package ru.Shniros.DAL.DAO;

import ru.Shniros.DAL.DAO.exception.CommonDaoException;
import ru.Shniros.DAL.iDao;
import ru.Shniros.domain.Account;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class AccountDao implements iDao<Account, Long> {
    private static final String TABLE_NAME = "finance.account";
    private final DataSource dataSource;

    public AccountDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Account> findAllByPersonId(Integer personId) throws CommonDaoException {
        List<Account> accounts = null;
        final String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE person_id = ?";
        try ( Connection connection = dataSource.getConnection();
              PreparedStatement ps = connection.prepareStatement(query))
        {

            ps.setInt(1,personId);
            ResultSet rs = ps.executeQuery();
            accounts = new ArrayList<Account>();
            while(rs.next()){
                accounts.add(new Account().setId(rs.getInt("id"))
                        .setName(rs.getString("balance"))
                        .setBalance(rs.getBigDecimal("balance"))
                        .setPersonId(rs.getInt("person_id")));
            }
        } catch (SQLException ex) {
            throw new CommonDaoException("Cannot find all by person_id",ex);
        }
        return accounts;
    }
    @Override
    public List<Account> findByAll() throws CommonDaoException {
        List<Account> accounts = null;
        final String query = "SELECT * FROM " + TABLE_NAME;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query))
        {
            ResultSet rs = ps.executeQuery();
            accounts = new ArrayList<Account>();
            while(rs.next()){
                accounts.add(new Account().setId(rs.getInt("id"))
                        .setName(rs.getString("balance"))
                        .setBalance(rs.getBigDecimal("balance"))
                        .setPersonId(rs.getInt("person_id")));
            }
        } catch (SQLException ex) {
            throw new CommonDaoException("Cannot find all accounts",ex);
        }
        return accounts;
    }

    @Override
    public Account findById(Long id) throws CommonDaoException {
        Account account = null;
        final String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query))
        {
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                account = new Account();
                account.setId(rs.getInt("id"));
                account.setName(rs.getString("name"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setPersonId(rs.getInt("person_id"));
            }
        } catch (SQLException ex) {
            throw new CommonDaoException("Cannot find by id",ex);
        }
        return account;
    }

    @Override
    public Account insert(Account account, Connection connection) throws CommonDaoException {
        final String query = "INSERT INTO " + TABLE_NAME +
                "(name, balance, person_id)" +
                " VALUES (?,?,?)";
        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, account.getName());
            ps.setBigDecimal(2, account.getBalance());
            ps.setInt(3, account.getPersonId());
            ps.execute();
        }catch (SQLException ex){
            throw new CommonDaoException("Cannot insert account",ex);
        }
        return account;
    }

    @Override
    public Account update(Account account, Connection connection) throws CommonDaoException {
        final String query = "UPDATE " + TABLE_NAME +
                " SET balance = ?" +
                " WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.setBigDecimal(1,account.getBalance());
            ps.setLong(2,account.getId());
            ps.execute();
        }catch (SQLException ex){
            throw new CommonDaoException("Cannot update account",ex);
        }
        ;
        return account;
    }

    @Override
    public boolean delete(Long id, Connection connection){
        return false;
    }

    public Integer countAccountByPersonId(Integer personId){
        final String query = "SELECT count(*) FROM " + TABLE_NAME +
                " WHERE person_id = ?";
        try (Connection connection = dataSource.getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1,personId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Integer result = rs.getInt("count");
                return result;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
