package ru.Shniros.service;

import ru.Shniros.DBase.DAO.DaoFactory;

import java.sql.SQLException;

public class ServiceFactory {
    private static AccountService accountService;
    private static TransactionService transactionService;
    private static SecurityService securityService;
    private static DigestService digestService;

    public static TransactionService getTransactionService() throws SQLException {
        if(transactionService == null){
            transactionService = new TransactionService(DaoFactory.getAccountDao(),DaoFactory.getTransactionDao());
        }
        return transactionService;
    }
    public static AccountService getAccountService() throws SQLException {
        if(accountService == null){
            accountService = new AccountService(DaoFactory.getAccountDao());
        }
        return accountService;
    }
    public static SecurityService getSecurityService() throws SQLException {
        if(securityService == null){
            securityService = new SecurityService(DaoFactory.getPersonDao(), getDigestService());
        }
        return securityService;
    }
    public static DigestService getDigestService(){
        if(digestService == null){
            digestService = new DigestService();
        }
        return digestService;
    }
}
