package ru.Shniros.DBase.DAO;

import ru.Shniros.DBase.domain.Account;

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

    public List<Account> findAllByPersonId(Integer personId){
        List<Account> accounts = new ArrayList<Account>();
        final String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE person_id = ?";
        try ( Connection connection = dataSource.getConnection()){
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
        return null;
    }
    @Override
    public List<Account> findByAll() {
        List<Account> accounts = new ArrayList<Account>();
        final String query = "SELECT * FROM " + TABLE_NAME;
        try (Connection connection = dataSource.getConnection()){


            PreparedStatement ps = connection.prepareStatement(query);
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
        return null;
    }

    @Override
    public Account findById(Long id) {
        final String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE id = ?";
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            Account account = new Account();
            if(rs.next()){
                account.setId(rs.getInt("id"));
                account.setName(rs.getString("name"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setPirsonId(rs.getInt("person_id"));
                return account;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Account insert(Account account, Connection connection) throws SQLException {
        final String query = "INSERT INTO " + TABLE_NAME +
                "(name, balance, person_id)" +
                " VALUES (?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,account.getName());
        ps.setBigDecimal(2,account.getBalance());
        ps.setInt(3,account.getPirsonId());
        ps.execute();
        return account;
    }

    @Override
    public Account update(Account account, Connection connection) throws SQLException {
        final String query = "UPDATE " + TABLE_NAME +
                " SET balance = ?" +
                " WHERE id = ?";

        PreparedStatement ps = null;
        ps = connection.prepareStatement(query);
        ps.setBigDecimal(1,account.getBalance());
        ps.setLong(2,account.getId());
        ps.execute();
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
