package com.musala.testRest.restapi.projects;

import com.musala.testRest.restapi.Utils;

import javax.persistence.*;
import javax.ws.rs.*;
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

    private Utils utils = Utils.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
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
    @Produces(MediaType.APPLICATION_JSON)
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ProjectDTO persistProject(ProjectDTO body) {

        try {

            Project project = (Project) utils.convertToEntity(body);
            em.getTransaction().begin();
            em.persist(project);
            em.getTransaction().commit();
            body.setId(project.getId());
            em.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
        return body;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public ProjectDTO updateProjectById(ProjectDTO body, @PathParam("id") int projectId) {

        Query q = em.createNamedQuery(PROJECT_UPDATE_PROJECT_BY_ID);
        em.getTransaction().begin();
        int updateCount = q.setParameter("l", body.getLabel())
                .setParameter("b", body.getBudget())
                .setParameter("mId", body.getManagerId())
                .setParameter("id", projectId)
                .executeUpdate();
        em.getTransaction().commit();
        em.close();

        if (updateCount == 1) {
            return body;
        } else {
            return new ProjectDTO();
        }
    }
}
