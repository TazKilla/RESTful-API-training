package com.musala.testRest.restapi;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.musala.testRest.restapi.User.USER_GET_USERS;
import static com.musala.testRest.restapi.User.USER_GET_USER_BY_ID;
import static com.musala.testRest.restapi.User.USER_UPDATE_USER_BY_ID;

@NamedQueries({
        @NamedQuery(
                name = USER_GET_USERS,
                query = "SELECT u " +
                        "FROM User u"),
        @NamedQuery(
                name = USER_GET_USER_BY_ID,
                query = "SELECT u " +
                        "FROM User u " +
                        "WHERE u.id = :id"),
        @NamedQuery(
                name = USER_UPDATE_USER_BY_ID,
                query = "UPDATE User u " +
                        "SET u.firstName = :fn, " +
                        "u.lastName = :ln, " +
                        "u.age = :a, " +
                        "u.profession = :p " +
                        "WHERE u.id = :id")
})

@Entity
@Table(name = "user", schema = "public")
public class User implements Serializable {

    public static final String USER_GET_USERS = "User.getUsers";
    public static final String USER_GET_USER_BY_ID = "User.getUserById";
    public static final String USER_UPDATE_USER_BY_ID = "User.updateUserById";

    @Id
    @Column(name="id")
    @GeneratedValue
    private int id;
    @Column(name="firstName")
    private String firstName = "NA";
    @Column(name="lastName")
    private String lastName = "NA";
    @Column(name="age")
    private int age = -1;
    @Column(name="profession")
    private String profession = "NA";
    @OneToMany(targetEntity = Project.class)
    private List projectList;

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

    public List getProjectList() {
        return projectList;
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

    public void setProjectList(List projectList) {
        this.projectList = projectList;
    }

    @Override
    public String toString() {
        return "First name: " + this.firstName +
                "\nLast name: " + this.lastName +
                "\nAge: " + this.age +
                "\nProfession: " + this.profession;
    }
}
