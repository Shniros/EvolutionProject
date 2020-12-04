package ru.Shniros.service;

import org.springframework.util.DigestUtils;

public class DigestService extends DigestUtils {

    public String getMd5(String password){
        return md5DigestAsHex(password.getBytes());
    }
}
