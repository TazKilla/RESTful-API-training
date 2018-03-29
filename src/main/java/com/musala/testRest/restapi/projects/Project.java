package com.musala.testRest.restapi.projects;

import com.musala.testRest.restapi.users.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "project", schema = "public")
public class Project implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue
    private int id = -1;
    @Column(name="label")
    @Basic
    private String label = "NA";
    @Column(name="budget")
    private float budget = -1;
    @ManyToOne
    private User user = null;
//    @Column(name="managerId")
//    private int managerId = -1;

    public Project() {

    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public float getBudget() {
        return budget;
    }

    public User getUser() {
        return user;
    }

    //    public int getManagerId() {
//        return managerId;
//    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //    public void setManagerId(int managerId) {
//        this.managerId = managerId;
//    }
}