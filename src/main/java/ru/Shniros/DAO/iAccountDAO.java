package ru.Shniros.DAO;

import java.util.LinkedList;

public interface iAccountDAO <DOMAIN,ID>{
    LinkedList<DOMAIN> findById(ID id);
    boolean insertAccount(DOMAIN domain);
    boolean updateAccount(DOMAIN domain);
    boolean deleteAccount(DOMAIN domain);
}