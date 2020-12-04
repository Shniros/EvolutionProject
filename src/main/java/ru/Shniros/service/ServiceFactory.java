package ru.Shniros.service;

import org.springframework.util.DigestUtils;
import ru.Shniros.DAL.DAO.DaoFactory;
import ru.Shniros.converter.AccountToAccountDto;
import ru.Shniros.converter.PersonToPersonDto;
import ru.Shniros.converter.TransactionToTransactionDto;
import ru.Shniros.service.exception.CommonServiceException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServiceFactory {
    private static AccountService accountService;
    private static TransactionService transactionService;
    private static SecurityService securityService;
    private static DigestService digestService;

    public static TransactionService getTransactionService(){
        if(transactionService == null){
            transactionService = new TransactionService(DaoFactory.getAccountDao(),
                    DaoFactory.getTransactionDao(),
                    DaoFactory.getDataSource(),
                    new TransactionToTransactionDto());
        }
        return transactionService;
    }
    public static AccountService getAccountService(){
        if(accountService == null){
            accountService = new AccountService(DaoFactory.getAccountDao(),
                    DaoFactory.getDataSource(),
                    new AccountToAccountDto());
        }
        return accountService;
    }
    public static SecurityService getSecurityService() throws CommonServiceException {
        if(securityService == null){
            securityService = new SecurityService(DaoFactory.getPersonDao(),
                    getDigestService(),
                    DaoFactory.getDataSource(),
                    new PersonToPersonDto());
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
