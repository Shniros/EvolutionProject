package dataSource.Shniros.DBase.DAO;

import dataSource.Shniros.DBase.domain.TransactionCategory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionCategoryDao implements iDao<TransactionCategory, Integer> {
    private final String TABLE_NAME = "finance.transaction_category";
    private final DataSource dataSource;
    public TransactionCategoryDao(DataSource dataSource){
        this.dataSource = dataSource;
    }
    @Override
    public TransactionCategory findById(Integer id) {
        final String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE id = ?";
        try (Connection connection = dataSource.getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            TransactionCategory category = new TransactionCategory();
            if(rs.next()){
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                return category;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TransactionCategory> findByAll() {
        final String query = "SELECT * FROM " + TABLE_NAME;
        try (Connection connection = dataSource.getConnection()){
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
        final String query = "INSERT INTO " + TABLE_NAME +
                " (name)" +
                " VALUES (?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,category.getName());
        ps.execute();
        return category;
    }

    @Override
    public TransactionCategory update(TransactionCategory category, Connection connection) throws SQLException {
        final String query = "UPDATE " + TABLE_NAME +
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