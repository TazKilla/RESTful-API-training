package com.musala.testRest.restapi.users;

import com.musala.testRest.restapi.Utils;
import com.musala.testRest.restapi.projects.Project;
import com.musala.testRest.restapi.projects.ProjectDTO;

import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import static com.musala.testRest.restapi.users.User.*;

// The Java class will be hosted at the URI path "/users"
@Path("/users")
public class Users {

//    @PersistenceUnit(unitName = "RESTDB")
//    private EntityManagerFactory emf;

    private String persistenceUnitName = "RESTDB";
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
    private EntityManager em = factory.createEntityManager();

    private Utils utils = new Utils();

//     The Java method will process HTTP POST requests
    @POST
//     The Java method will process content identified by the MIME Media type "application/json"
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public UserDTO persistUser(UserDTO body) {

        try {

            User user = (User) utils.convertToEntity(body);
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            body.setId(user.getId());
            em.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
        return body;
    }

    @GET
    @Produces( MediaType.APPLICATION_JSON)
    public List<UserDTO> getUsers() {

        Query q = em.createNamedQuery(USER_GET_USERS);
        List<User> users = q.getResultList();
        em.close();

        List<UserDTO> userList = new ArrayList<UserDTO>();

        for (User user : users) {
            userList.add((UserDTO) utils.convertToDto(user));
        }

        return userList;
    }

    @GET
    @Produces( MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public UserDTO getUserById(@PathParam("id") int userId) {

        User user = null;
        try {
            Query q = em.createNamedQuery(USER_GET_USER_BY_ID).setParameter("id", userId);
            user = (User) q.getSingleResult();
            em.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        if (user != null) {
            return (UserDTO) utils.convertToDto(user);
        } else {
            return new UserDTO();
        }
    }

    @GET
    @Produces( MediaType.APPLICATION_JSON)
    @Path("/{id}/projects")
    public List<ProjectDTO> getProjectsByUserId(@PathParam("id") int userId) {

        Query q = em.createNamedQuery(USER_GET_PROJECTS_BY_USER_ID).setParameter("mId", userId);
        List<Project> projects = q.getResultList();
        em.close();

        List<ProjectDTO> projectList = new ArrayList<ProjectDTO>();

        for (Project project : projects) {
            projectList.add((ProjectDTO) utils.convertToDto(project));
        }

        return projectList;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDTO updateUserById(UserDTO body) {

        Query q = em.createNamedQuery(USER_UPDATE_USER_BY_ID);
        em.getTransaction().begin();
        int updateCount = q.setParameter("fn", body.getFirstName())
                .setParameter("ln", body.getLastName())
                .setParameter("a", body.getAge())
                .setParameter("p", body.getProfession())
                .setParameter("id", body.getId())
                .executeUpdate();
        em.getTransaction().commit();
        em.close();

        if (updateCount == 1) {
            return body;
        } else {
            return new UserDTO();
        }
    }
}
