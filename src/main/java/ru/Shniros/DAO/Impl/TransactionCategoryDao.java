package ru.Shniros.DAO.Impl;

import ru.Shniros.DAO.domain.TransactionCategory;
import ru.Shniros.DAO.iDao;
import ru.Shniros.DAO.jdbc.SingleConnectionManager;

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
        Connection connection = null;
        try {
            connection = SingleConnectionManager.getConnection();
            String query = "SELECT * FROM " + TABLE_NAME;
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            List<TransactionCategory> categories = new ArrayList<TransactionCategory>();
            while (rs.next()){
                categories.add(new TransactionCategory().setId(rs.getInt("id"))
                                                        .setName("name"));
            }
            return categories;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public TransactionCategory insert(TransactionCategory category, Connection connection) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME +
                        " (name)" +
                        " VALUES (?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,category.getName());
        ps.execute();
        return category;
    }

    @Override
    public TransactionCategory update(TransactionCategory category, Connection connection) throws SQLException {
        String query = "UPDATE " + TABLE_NAME +
                " SET name = ?" +
                " WHERE = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,category.getName());
        ps.setInt(2,category.getId());
        ps.execute();
        return category;
    }

    @Override
    public boolean delete(Integer integer, Connection connection) {
        return false;
    }
}
