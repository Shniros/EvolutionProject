package ru.Shniros.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CommonServiceException extends Exception{
    private static final Logger logger = Logger.getLogger(CommonServiceException.class.getName());
    public CommonServiceException(String message,Exception ex){
        logger.log(Level.WARNING,message,ex);
    }
    public CommonServiceException(String message){
        logger.log(Level.WARNING,message);
    }
}