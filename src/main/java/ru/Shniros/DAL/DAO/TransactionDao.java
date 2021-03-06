package ru.Shniros.DAL.DAO;

import ru.Shniros.DAL.DAO.exception.CommonDaoException;
import ru.Shniros.DAL.iDao;
import ru.Shniros.domain.Transaction;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao implements iDao<Transaction,Long> {
    private static final String TABLE_NAME = "finance.transaction";
    private final DataSource dataSource;
    public TransactionDao(DataSource dataSource){
        this.dataSource = dataSource;
    }
    @Override
    public Transaction findById(Long id) throws CommonDaoException {
        Transaction transaction = null;
        final String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(query))
        {
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setDate(rs.getDate("date"));
                transaction.setFromAccountId(rs.getLong("from_account_id"));
                transaction.setToAccountId(rs.getLong("to_account_id"));
                transaction.setSum(rs.getBigDecimal("sum"));
                transaction.setComment(rs.getString("comment"));
                transaction.setCategoryId(rs.getInt("category_id"));
            }
        } catch (SQLException ex) {
            throw new CommonDaoException("Cannot find transaction by id",ex);
        }
        return transaction;
    }

    @Override
    public List<Transaction> findByAll() throws CommonDaoException {
        List<Transaction> list = new ArrayList<Transaction>();
        final String query = "SELECT * FROM " + TABLE_NAME;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query))
        {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Transaction().setId(rs.getInt("id"))
                                .setDate(rs.getDate("date"))
                                .setFromAccountId(rs.getLong("from_account_id"))
                                .setToAccountId(rs.getLong("to_account_id"))
                                .setSum(rs.getBigDecimal("sum"))
                                .setComment(rs.getString("comment"))
                                .setCategoryId(rs.getInt("category_id")));
            }
        } catch (SQLException ex) {
            throw new CommonDaoException("Cannot find all transaction",ex);
        }
        return list;
    }

    @Override
    public Transaction insert(Transaction transaction) throws CommonDaoException {
        final String query =  "INSERT INTO " + TABLE_NAME +
                "(date," +
                "comment," +
                "from_account_id," +
                "to_account_id," +
                "sum," +
                "category_id)" +
                " VALUES (?,?,?,?,?,?)";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(query))
        {
            ps.setTimestamp(1, new Timestamp(transaction.getDate().getTime()));
            ps.setString(2, transaction.getComment());
            ps.setLong(3, transaction.getFromAccountId());
            ps.setLong(4, transaction.getToAccountId());
            ps.setBigDecimal(5, transaction.getSum());
            ps.setInt(6, transaction.getCategoryId());
            ps.execute();
        }catch (SQLException ex){
            throw new CommonDaoException("Cannot insert transaction",ex);
        }
        return transaction;
    }

    @Override
    public Transaction update(Transaction transaction) throws CommonDaoException {
        final String query = "UPDATE " + TABLE_NAME +
                " SET date = ?," +
                "comment = ?," +
                "from_account_id = ?," +
                "to_account_id = ?" +
                "sum = ?" +
                "category_id = ?" +
                " WHERE id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(query))
        {
            ps.setTimestamp(1, new Timestamp(transaction.getDate().getTime()));
            ps.setString(2, transaction.getComment());
            ps.setLong(3, transaction.getFromAccountId());
            ps.setLong(4, transaction.getToAccountId());
            ps.setBigDecimal(5, transaction.getSum());
            ps.setInt(6, transaction.getCategoryId());
            ps.setLong(7, transaction.getId());
            ps.execute();
        }catch (SQLException ex){
            throw new CommonDaoException("Cannot update transaction",ex);
        }
        return transaction;
    }

    public Transaction insert(Transaction transaction, Connection connection) throws SQLException {
        final String query =  "INSERT INTO " + TABLE_NAME +
                "(date," +
                "comment," +
                "from_account_id," +
                "to_account_id," +
                "sum," +
                "category_id)" +
                " VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setTimestamp(1, new Timestamp(transaction.getDate().getTime()));
        ps.setString(2, transaction.getComment());
        ps.setLong(3, transaction.getFromAccountId());
        ps.setLong(4, transaction.getToAccountId());
        ps.setBigDecimal(5, transaction.getSum());
        ps.setInt(6, transaction.getCategoryId());
        ps.execute();

        return transaction;
    }

    public Transaction update(Transaction transaction, Connection connection) throws SQLException {
        final String query = "UPDATE " + TABLE_NAME +
                " SET date = ?," +
                "comment = ?," +
                "from_account_id = ?," +
                "to_account_id = ?" +
                "sum = ?" +
                "category_id = ?" +
                " WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setTimestamp(1, new Timestamp(transaction.getDate().getTime()));
        ps.setString(2, transaction.getComment());
        ps.setLong(3, transaction.getFromAccountId());
        ps.setLong(4, transaction.getToAccountId());
        ps.setBigDecimal(5, transaction.getSum());
        ps.setInt(6, transaction.getCategoryId());
        ps.setLong(7, transaction.getId());
        ps.execute();

        return transaction;
    }

    @Override
    public boolean delete(Long aLong, Connection connection) {
        return false;
    }
}
