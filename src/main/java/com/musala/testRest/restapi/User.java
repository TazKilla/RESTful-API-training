package com.musala.testRest.restapi;

import javax.persistence.*;
import java.io.Serializable;

import static com.musala.testRest.restapi.User.USER_GET_USERS;

@NamedQueries({
        @NamedQuery(name = USER_GET_USERS,
        query = "SELECT u FROM User u")
})

@Entity
@Table(name = "user", schema = "public")
public class User implements Serializable {

    public  static final String USER_GET_USERS = "User.getUsers";

    @Id
    @Column(name="id")
    @GeneratedValue
    private int id;
    @Column(name="firstName")
    private String firstName;
    @Column(name="lastName")
    private String lastName;
    @Column(name="age")
    private int age;
    @Column(name="profession")
    private String profession;

    public User() {

    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getProfession() {
        return profession;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    public String toString() {
        return "First name: " + this.firstName +
                "\nLast name: " + this.lastName +
                "\nAge: " + this.age +
                "\nProfession: " + this.profession;
    }
}
