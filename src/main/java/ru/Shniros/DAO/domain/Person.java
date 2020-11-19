package ru.Shniros.DAO.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.DigestUtils;

@Data
@Accessors(chain = true)
public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int id;

    public Person(){}
    public Person(String email,String password){
        this.email = email;
        this.password = DigestUtils.md5DigestAsHex(password.getBytes());
    }
}