package ru.Shniros.DBase.DAO;

import ru.Shniros.DBase.domain.TransactionCategory;
import ru.Shniros.DBase.DAO.iDao;
import ru.Shniros.DBase.jdbc.SingleConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionCategoryDao implements iDao<TransactionCategory, Integer> {
    private final String TABLE_NAME = "finance.transaction_category";
    @Override
    public TransactionCategory findById(Integer id) {
        Connection connection = null;
        try {
            connection = SingleConnectionManager.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME +
                    " WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            TransactionCategory category = new TransactionCategory();
            while (rs.next()){
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
            }
            return category;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TransactionCategory> findByAll() {
        return null;
    }

    @Override
    public TransactionCategory insert(TransactionCategory transactionCategory, Connection connection) throws SQLException {
        return null;
    }

    @Override
    public TransactionCategory update(TransactionCategory transactionCategory, Connection connection) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(Integer integer, Connection connection) {
        return false;
    }
}
