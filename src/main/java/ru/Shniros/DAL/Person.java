package ru.Shniros.DAL;

import org.springframework.util.DigestUtils;
import ru.Shniros.jdbc.SingleConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int id;
    private int balance = 0;
    private int age;

    public Person(){}
    public Person(String email,String password){
        this.email = email;
        this.password = DigestUtils.md5DigestAsHex(password.getBytes());
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public int getId() {
        return id;
    }
    public int getBalance() {
        return balance;
    }
    public int getAge() {
        return age;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return  "id = " + id + "\n" +
                "First name = " + firstName + "\n" +
                "Last name = " + lastName + "\n" +
                "E-mail = " + email +  "\n" +
                "password = " + password + "\n" +
                "balance = " + balance +"\n"+
                "age = " + age + "\n";
    }
}
