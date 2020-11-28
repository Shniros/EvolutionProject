package ru.Shniros.service;

import org.springframework.util.DigestUtils;

public class DigestService {
    public String getHash(String password){
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
