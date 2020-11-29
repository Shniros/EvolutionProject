package ru.Shniros.DAL.DAO.exception;
@SuppressWarnings("serial")
public class CommonDaoException extends Exception{
    public CommonDaoException(){
        super();
    }
    public CommonDaoException(String message){
        super(message);
    }
    public CommonDaoException(String message, Throwable ex){
        super(message,ex);
    }

}
