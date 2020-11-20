package ru.Shniros.DAO.Impl;

import ru.Shniros.DAO.domain.Account;
import ru.Shniros.DAO.iAccountDAO;
import ru.Shniros.DAO.jdbc.SingleConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements iAccountDAO<Account, Integer> {
    private final String TABLE_NAME = "finance.account";
    private static Connection connection;
    @Override
    public List<Account> findById(Integer personId) {
        List<Account> accounts = new ArrayList<Account>();
        try {
            connection = SingleConnectionManager.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME +
                           " WHERE person_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1,personId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                accounts.add(new Account().setId(rs.getInt("id"))
                                          .setName(rs.getString("balance"))
                                          .setBalance(rs.getBigDecimal("balance"))
                                          .setPirsonId(rs.getInt("person_id")));
            }
            return accounts;
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException conEx) {
               conEx.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean insertAccount(Account account) {
        try {
            connection = SingleConnectionManager.getConnection();
            connection.setAutoCommit(false);
            String query = "INSERT INTO " + TABLE_NAME +
                           "(name, balance, person_id)" +
                           " VALUES (?,?,?);";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,account.getName());
            ps.setBigDecimal(2,account.getBalance());
            ps.setInt(3,account.getPirsonId());
            ps.execute();
            connection.commit();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException conEx) {
                conEx.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean updateAccount(Account account) {
        try {
            connection = SingleConnectionManager.getConnection();
            connection.setAutoCommit(false);
            String query = "UPDATE " + TABLE_NAME +
                           " balance = ?" +
                           " WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,account.getName());
            ps.setBigDecimal(2,account.getBalance());
            ps.setLong(3,account.getId());
            ps.execute();
            connection.commit();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException conEx) {
                conEx.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean deleteAccount(Account account) {
        //TODO
        return false;
    }
}
