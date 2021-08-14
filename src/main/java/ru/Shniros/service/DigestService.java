package ru.Shniros.service;

import org.springframework.util.DigestUtils;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class DigestService extends DigestUtils {
    public DigestService() {
    }

    public String getMd5(String password) {
        return md5DigestAsHex(password.getBytes());
    }
}
