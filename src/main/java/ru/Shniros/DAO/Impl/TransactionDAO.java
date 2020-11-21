package ru.Shniros.DAO.Impl;

import ru.Shniros.DAO.domain.Transaction;
import ru.Shniros.DAO.iDao;

import java.sql.Connection;
import java.util.List;

public class TransactionDAO implements iDao<Transaction,Integer> {


    @Override
    public Transaction findById(Integer id) {
        return null;
    }

    @Override
    public List<Transaction> findByAll() {
        return null;
    }

    @Override
    public Transaction insert(Transaction transaction, Connection connection) {
        return null;
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
