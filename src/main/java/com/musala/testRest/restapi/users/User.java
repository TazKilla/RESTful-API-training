package com.musala.testRest.restapi.users;

import com.musala.testRest.restapi.projects.Project;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

import static com.musala.testRest.restapi.users.User.*;

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
                        "WHERE u.id = :id"),
        @NamedQuery(
                name = USER_GET_PROJECTS_BY_USER_ID,
                query = "SELECT p " +
                        "FROM Project p " +
                        "WHERE p.managerId = :mId")
})

@Entity
@Table(name = "user", schema = "public")
public class User implements Serializable {

    static final String USER_GET_USERS = "Users.getUsers";
    static final String USER_GET_USER_BY_ID = "Users.getUserById";
    static final String USER_GET_PROJECTS_BY_USER_ID = "Users.getProjectsByUserId";
    static final String USER_UPDATE_USER_BY_ID = "Users.updateUserById";

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
//    @OneToMany(mappedBy = "user")
//    @MapKey(name = "label")
//    private Map<String, Project> projects;

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

//    public List getProjectList() {
//        return projectList;
//    }

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

//    public void setProjectList(List projectList) {
//        this.projectList = projectList;
//    }

    @Override
    public String toString() {
        return "First name: " + this.firstName +
                "\nLast name: " + this.lastName +
                "\nAge: " + this.age +
                "\nProfession: " + this.profession;
    }
}
