package ru.Shniros.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CommonServiceException extends Exception{
    private static Logger logger;
    public CommonServiceException(String className,String message){
        logger = Logger.getLogger(className);
        logger.log(Level.WARNING,message);
    }
    public CommonServiceException(String className,String message,Exception ex){
        logger = Logger.getLogger(className);
        logger.log(Level.WARNING,message,ex);
    }
}