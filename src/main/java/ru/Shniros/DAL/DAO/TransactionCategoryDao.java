package ru.Shniros.DAL.DAO;

import ru.Shniros.DAL.DAO.exception.CommonDaoException;
import ru.Shniros.DAL.iDao;
import ru.Shniros.domain.TransactionCategory;

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
    public TransactionCategory findById(Integer id) throws CommonDaoException {
        TransactionCategory category = null;
        final String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query))
        {
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                category = new TransactionCategory();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
            }
        } catch (SQLException ex) {
            throw new CommonDaoException("Cannot find category by id",ex);
        }
        return category;
    }

    @Override
    public List<TransactionCategory> findByAll() throws CommonDaoException {
        List<TransactionCategory> categories = new ArrayList<>();
        final String query = "SELECT * FROM " + TABLE_NAME;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query))
        {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                categories.add(new TransactionCategory().setId(rs.getInt("id"))
                        .setName("type_category"));
            }
        } catch (SQLException ex) {
            throw new CommonDaoException("Cannot find all categories",ex);
        }
        return categories;
    }

    @Override
    public TransactionCategory insert(TransactionCategory category) throws CommonDaoException {
        final String query = "INSERT INTO " + TABLE_NAME +
                " (type_category)" +
                " VALUES (?)";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(query))
        {
            ps.setString(1, category.getName());
            ps.execute();
        }catch (SQLException ex){
            throw new CommonDaoException("Cannot insert category",ex);
        }
        return category;
    }

    @Override
    public TransactionCategory update(TransactionCategory category) throws CommonDaoException {
        final String query = "UPDATE " + TABLE_NAME +
                " SET type_category = ?" +
                " WHERE = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(query))
        {
            ps.setString(1, category.getName());
            ps.setInt(2, category.getId());
            ps.execute();
        }catch (SQLException ex){
            throw new CommonDaoException("Cannot update category",ex);
        }
        return category;
    }

    @Override
    public boolean delete(Integer integer, Connection connection) {
        return false;
    }
}