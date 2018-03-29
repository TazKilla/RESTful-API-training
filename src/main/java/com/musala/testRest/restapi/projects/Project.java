package com.musala.testRest.restapi.projects;

import javax.persistence.*;
import java.io.Serializable;

import static com.musala.testRest.restapi.projects.Project.PROJECT_GET_PROJECTS;
import static com.musala.testRest.restapi.projects.Project.PROJECT_GET_PROJECT_BY_ID;
import static com.musala.testRest.restapi.projects.Project.PROJECT_UPDATE_PROJECT_BY_ID;

@NamedQueries({
        @NamedQuery(
                name = PROJECT_GET_PROJECTS,
                query = "SELECT p " +
                        "FROM Project p"),
        @NamedQuery(
                name = PROJECT_GET_PROJECT_BY_ID,
                query = "SELECT p " +
                        "FROM Project p " +
                        "WHERE p.id = :id"),
        @NamedQuery(
                name = PROJECT_UPDATE_PROJECT_BY_ID,
                query = "UPDATE Project p " +
                        "SET p.label = :l, " +
                        "p.budget = :b, " +
                        "p.managerId = :mId " +
                        "WHERE p.id = :id")
})

@Entity
@Table(name = "project", schema = "public")
public class Project implements Serializable {

    static final String PROJECT_GET_PROJECTS = "Projects.getProjects";
    static final String PROJECT_GET_PROJECT_BY_ID = "Projects.getProjectById";
    static final String PROJECT_UPDATE_PROJECT_BY_ID = "Projects.updateProjectById";

    @Id
    @Column(name="id")
    @GeneratedValue
    private int id;
    @Column(name="label")
    private String label = "NA";
    @Column(name="budget")
    private float budget = -1;
//    @ManyToOne
//    private User user = null;
    @Column(name="managerid")
    private int managerId = -1;

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

//    public User getUser() {
//        return user;
//    }

    public int getManagerId() {
        return managerId;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

//    public void setUser(User user) {
//        this.user = user;
//    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "Label: " + this.label +
                "\nBudget: " + this.budget +
                "\nManager ID: " + this.managerId;
    }
}