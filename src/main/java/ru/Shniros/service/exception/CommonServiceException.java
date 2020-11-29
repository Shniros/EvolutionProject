package ru.Shniros.service.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CommonServiceException extends Exception{

    public CommonServiceException(){
        super();
    }
    public CommonServiceException(String message){
        super(message);
    }
    public CommonServiceException(String message, Throwable ex){
        super(message,ex);
    }
}