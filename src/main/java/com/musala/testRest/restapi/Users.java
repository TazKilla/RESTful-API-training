package com.musala.testRest.restapi;

import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import static com.musala.testRest.restapi.User.USER_GET_USERS;
import static com.musala.testRest.restapi.User.USER_GET_USER_BY_ID;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/users")
public class Users {

//    @PersistenceContext(name = "users")
    private EntityManager em;
    private String persistenceUnitName = "users";
    private EntityManagerFactory factory;
//     The Java method will process HTTP POST requests
    @POST
//     The Java method will process content identified by the MIME Media type "application/json"
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
//    TODO: add transfer object here
    public UserDTO persistUser(UserDTO body) {

        try {

            factory = Persistence.createEntityManagerFactory(persistenceUnitName);
            em = factory.createEntityManager();

            User user = convertToEntity(body);
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();

            em.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return body;
    }

    @GET
    @Produces( MediaType.APPLICATION_JSON)
    public List<UserDTO> getUsers() {

        factory = Persistence.createEntityManagerFactory(persistenceUnitName);
        em = factory.createEntityManager();
        Query q = em.createNamedQuery(USER_GET_USERS);
        List<User> users = q.getResultList();
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

        factory = Persistence.createEntityManagerFactory(persistenceUnitName);
        em = factory.createEntityManager();
        Query q = em.createNamedQuery(USER_GET_USER_BY_ID).setParameter("id", userId);
        User user = (User) q.getSingleResult();

        return convertToDto(user);
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
