package ru.Shniros.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.springframework.util.DigestUtils.md5DigestAsHex;

class DigestServiceTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getMd5() {
        String md5passwordHash = "5f4dcc3b5aa765d61d8327deb882cf99";
        String password = "password";
        String hash = md5DigestAsHex(password.getBytes());
        Assert.assertEquals(md5passwordHash,hash);
    }

}