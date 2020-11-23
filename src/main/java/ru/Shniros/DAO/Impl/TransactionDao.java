package ru.Shniros.DAO.Impl;

import ru.Shniros.DAO.domain.Transaction;
import ru.Shniros.DAO.iDao;
import ru.Shniros.DAO.jdbc.SingleConnectionManager;
import ru.Shniros.exception.CommonServiceException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao implements iDao<Transaction,Long> {
    private static final String TABLE_NAME = "finance.transaction";

    @Override
    public Transaction findById(Long id) {
        Connection connection = null;
        try {
            connection = SingleConnectionManager.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME +
                           " WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            Transaction transaction = new Transaction();
            while(rs.next()){
                transaction.setId(rs.getInt("id"));
                transaction.setDate(rs.getDate("date"));
                transaction.setFromAccountId(rs.getLong("from_account_id"));
                transaction.setToAccountId(rs.getLong("to_account_id"));
                transaction.setSum(rs.getBigDecimal("sum"));
                transaction.setComment(rs.getString("comment"));
                transaction.setCategoryId(rs.getInt("category_id"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException ignored) {}
            }
        }
        return null;
    }

    @Override
    public List<Transaction> findByAll() {
        Connection connection = null;
        try {
            connection = SingleConnectionManager.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME;
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            List<Transaction> list = new ArrayList<Transaction>();
            while(rs.next()){
                list.add(new Transaction().setId(rs.getInt("id"))
                                .setDate(rs.getDate("date"))
                                .setFromAccountId(rs.getLong("from_account_id"))
                                .setToAccountId(rs.getLong("to_account_id"))
                                .setSum(rs.getBigDecimal("sum"))
                                .setComment(rs.getString("comment"))
                                .setCategoryId(rs.getInt("category_id")));
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException ignored) {}
            }
        }
        return null;
    }

    @Override
    public Transaction insert(Transaction transaction, Connection connection) throws SQLException {
        String query =  "INSERT INTO " + TABLE_NAME +
                        "(date," +
                        "comment," +
                        "from_account_id," +
                        "to_account_id," +
                        "sum," +
                        "category_id)" +
                       " VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setTimestamp(1,new Timestamp(transaction.getDate().getTime()));
        ps.setString(2,transaction.getComment());
        ps.setLong(3,transaction.getFromAccountId());
        ps.setLong(4,transaction.getToAccountId());
        ps.setBigDecimal(5,transaction.getSum());
        ps.setInt(6,transaction.getCategoryId());
        ps.execute();
        return transaction;
    }

    @Override
    public Transaction update(Transaction transaction, Connection connection) throws SQLException {
        String query = "UPDATE " + TABLE_NAME +
                " SET date = ?," +
                "comment = ?," +
                "from_account_id = ?," +
                "to_account_id = ?" +
                "sum = ?" +
                "category_id = ?" +
                " WHERE id = ?;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setTimestamp(1,new Timestamp(transaction.getDate().getTime()));
        ps.setString(2,transaction.getComment());
        ps.setLong(3,transaction.getFromAccountId());
        ps.setLong(4,transaction.getToAccountId());
        ps.setBigDecimal(5,transaction.getSum());
        ps.setInt(6,transaction.getCategoryId());
        ps.setLong(7,transaction.getId());
        ps.execute();
        return transaction;
    }

    @Override
    public boolean delete(Long aLong, Connection connection) {
        return false;
    }
}
