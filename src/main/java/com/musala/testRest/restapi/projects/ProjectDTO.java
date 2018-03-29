package com.musala.testRest.restapi.projects;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class ProjectDTO implements Serializable {

    private int id = -1;
    private String label = "NA";
    private float budget = -1;
    private int managerId = -1;

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public float getBudget() {
        return budget;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

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
