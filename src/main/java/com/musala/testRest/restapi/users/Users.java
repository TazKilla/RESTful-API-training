package com.musala.testRest.restapi.users;

import com.musala.testRest.restapi.Utils;
import com.musala.testRest.restapi.projects.Project;
import com.musala.testRest.restapi.projects.ProjectDTO;

import javax.ejb.Stateful;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import static com.musala.testRest.restapi.users.User.*;

// The Java class will be hosted at the URI path "/users"
//@Stateful
@Path("/users")
public class Users {

//    @PersistenceContext private EntityManager em;

    private String persistenceUnitName = "RESTDB";
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
    private EntityManager em = factory.createEntityManager();

    private Utils utils = Utils.getInstance();

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
    @Produces(MediaType.APPLICATION_JSON)
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
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search")
    public List<UserDTO> getUsersByCriteria(
            @QueryParam("firstName") String firstName,
            @QueryParam("lastName") String lastName,
            @QueryParam("age") int age,
            @QueryParam("maxAge") int maxAge,
            @QueryParam("minAge") int minAge,
            @QueryParam("profession") String profession) {

        String whereParams = "WHERE ";
        boolean firstParam = true;
        if (firstName != null) {
            whereParams += "lower(u.firstName) LIKE lower('%" + firstName + "%') ";
            firstParam = false;
        }
        if (lastName != null) {
            if (!firstParam) whereParams += "AND ";
            whereParams += "lower(u.lastName) LIKE lower('%" + lastName + "%') ";
            firstParam = false;
        }
        if (age != 0) {
            if (!firstParam) whereParams += "AND ";
            whereParams += "u.age = " + age + " ";
            firstParam = false;
        } else if (maxAge != 0 && minAge != 0) {
            if (!firstParam) whereParams += "AND ";
            whereParams += "u.age BETWEEN " + minAge + " AND " + maxAge + " ";
            firstParam = false;
        } else if (maxAge != 0) {
            if (!firstParam) whereParams += "AND ";
            whereParams += "u.age <= " + maxAge + " ";
            firstParam = false;
        } else if (minAge != 0) {
            if (!firstParam) whereParams += "AND ";
            whereParams += "u.age >= " + minAge + " ";
            firstParam = false;
        }
        if (profession != null) {
            if (!firstParam) whereParams += "AND ";
            whereParams += "lower(u.profession) LIKE lower('%" + profession + "%') ";
            firstParam = false;
        }

        String fullQuery = firstParam ? "SELECT u FROM User u" : "SELECT u FROM User u " + whereParams;
//        return fullQuery;
        List<UserDTO> userList = new ArrayList<UserDTO>();

        Query q = em.createQuery(fullQuery);
        List<User> users = q.getResultList();
        em.close();

        for (User user : users) {
            userList.add((UserDTO) utils.convertToDto(user));
        }

        return userList;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
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
    @Produces(MediaType.APPLICATION_JSON)
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/projects/{pId}")
    public ProjectDTO getProjectsByUserId(@PathParam("id") int userId, @PathParam("pId") int projectId) {

        Project project = null;

        try {
            Query q = em.createNamedQuery(USER_GET_PROJECT_BY_ID).setParameter("pId", projectId);
            project = (Project) q.getSingleResult();
            em.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (project != null) {
            return (ProjectDTO) utils.convertToDto(project);
        } else {
            return new ProjectDTO();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public UserDTO updateUserById(UserDTO body, @PathParam("id") int userId) {

        Query q = em.createNamedQuery(USER_UPDATE_USER_BY_ID);
        em.getTransaction().begin();
        int updateCount = q.setParameter("fn", body.getFirstName())
                .setParameter("ln", body.getLastName())
                .setParameter("a", body.getAge())
                .setParameter("p", body.getProfession())
                .setParameter("id", userId)
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
