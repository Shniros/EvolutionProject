package ru.Shniros.DAL.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int id;
}