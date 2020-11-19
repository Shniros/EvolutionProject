package ru.Shniros.DAO;

import java.util.List;

public interface iAccountDAO <DOMAIN,ID>{
    List<DOMAIN> findById(ID id);
    boolean insertAccount(DOMAIN domain);
    boolean updateAccount(DOMAIN domain);
    boolean deleteAccount(DOMAIN domain);
}