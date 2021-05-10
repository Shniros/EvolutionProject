package ru.Shniros.service;

import ru.Shniros.DAL.DAO.DaoFactory;
import ru.Shniros.service.exception.CommonServiceException;

public class ServiceFactory {
    private static AccountService accountService;
    private static TransactionService transactionService;
    private static SecurityService securityService;
    private static DigestService digestService;

    public static TransactionService getTransactionService(){
        if(transactionService == null){
            transactionService = new TransactionService(DaoFactory.getAccountDao(),
                    DaoFactory.getTransactionDao(),
                    DaoFactory.getDataSource());
        }
        return transactionService;
    }
    public static AccountService getAccountService(){
        if(accountService == null){
            accountService = new AccountService(DaoFactory.getAccountDao());
        }
        return accountService;
    }
    public static SecurityService getSecurityService() throws CommonServiceException {
        if(securityService == null){
            securityService = new SecurityService(DaoFactory.getPersonDao(),
                    getDigestService());
        }
        return securityService;
    }
    public static DigestService getDigestService() throws CommonServiceException {
        if(digestService == null){
            digestService = new DigestService();
        }
        return digestService;
    }
}
