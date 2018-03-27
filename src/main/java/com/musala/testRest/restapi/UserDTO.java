package com.musala.testRest.restapi;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class UserDTO implements Serializable {

    private String firstName = "NA";
    private String lastName = "NA";
    private int age = -1;
    private String profession = "NA";

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
