package ru.Shniros.DAO.Impl;

import ru.Shniros.DAO.domain.Transaction;
import ru.Shniros.DAO.iDao;
import ru.Shniros.DAO.jdbc.SingleConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class TransactionDAO implements iDao<Transaction,Integer> {
    private static final String TABLE_NAME = "finance.transaction";

    @Override
    public Transaction findById(Integer id) {
        return null;
    }

    @Override
    public List<Transaction> findByAll() {
        return null;
    }

    @Override
    public Transaction insert(Transaction transaction, Connection connection) throws SQLException {
        String query =  "INSERT INTO " + TABLE_NAME +
                        "(date," +
                        "comment," +
                        "from_account_id," +
                        "to_account_id," +
                        "sum)" +
                       " VALUES (?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setTimestamp(1,new Timestamp(transaction.getDate().getTime()));
        ps.setString(2,transaction.getComment());
        ps.setLong(3,transaction.getFromAccountId());
        ps.setLong(4,transaction.getToAccountId());
        ps.setBigDecimal(5,transaction.getSum());
        ps.execute();
        return transaction;
    }

    @Override
    public Transaction update(Transaction transaction, Connection connection) {
        return null;
    }

    @Override
    public boolean delete(Integer id, Connection connection) {
        return false;
    }
}
