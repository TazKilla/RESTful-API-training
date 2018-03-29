package com.musala.testRest.restapi.projects;

import com.musala.testRest.restapi.Utils;

import javax.persistence.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import static com.musala.testRest.restapi.projects.Project.PROJECT_GET_PROJECTS;
import static com.musala.testRest.restapi.projects.Project.PROJECT_GET_PROJECT_BY_ID;
import static com.musala.testRest.restapi.projects.Project.PROJECT_UPDATE_PROJECT_BY_ID;

@Path("/projects")
public class Projects {


    private String persistenceUnitName = "RESTDB";
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
    private EntityManager em = factory.createEntityManager();

    private Utils utils = new Utils();

    @GET
    @Produces( MediaType.APPLICATION_JSON)
    public List<ProjectDTO> getProjects() {

        Query q = em.createNamedQuery(PROJECT_GET_PROJECTS);
        List<Project> projects = q.getResultList();
        em.close();

        List<ProjectDTO> projectList = new ArrayList<ProjectDTO>();

        for (Project project : projects) {
            projectList.add((ProjectDTO) utils.convertToDto(project));
        }

        return projectList;
    }

    @GET
    @Produces( MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public ProjectDTO getProjectById(@PathParam("id") int projectId) {

        Project project = null;
        try {
            Query q = em.createNamedQuery(PROJECT_GET_PROJECT_BY_ID).setParameter("id", projectId);
            project = (Project) q.getSingleResult();
            em.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        if (project != null) {
            return (ProjectDTO) utils.convertToDto(project);
        } else {
            return new ProjectDTO();
        }
    }
}
