package com.musala.testRest.restapi;

import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static com.musala.testRest.restapi.User.USER_GET_USERS;
import static com.musala.testRest.restapi.User.USER_GET_USER_BY_ID;
import static com.musala.testRest.restapi.User.USER_UPDATE_USER_BY_ID;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/users")
public class Users {

//    @PersistenceUnit(unitName = "RESTDB")
//    private EntityManagerFactory emf;
//    @Context
//    private HttpServletResponse response;

    private String persistenceUnitName = "RESTDB";
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistenceUnitName);
    private EntityManager em = factory.createEntityManager();

//     The Java method will process HTTP POST requests
    @POST
//     The Java method will process content identified by the MIME Media type "application/json"
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public UserDTO persistUser(UserDTO body) {

        try {

            User user = convertToEntity(body);
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            body.setId(user.getId());
            em.close();
//            response.setStatus(Response.Status.CREATED.getStatusCode());

        } catch(Exception e) {
            e.printStackTrace();
//            response.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
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
            userList.add(convertToDto(user));
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

        }

        if (user != null) {
            return convertToDto(user);
        } else {
            return new UserDTO();
        }
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

    // Useful methods, User to UserDTO conversion

    private ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private UserDTO convertToDto(User user) {
        return modelMapper().map(user, UserDTO.class);
    }

    private User convertToEntity(UserDTO userDTO) {
        return modelMapper().map(userDTO, User.class);
    }
}
