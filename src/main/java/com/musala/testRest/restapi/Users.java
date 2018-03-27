package com.musala.testRest.restapi;

import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import static com.musala.testRest.restapi.User.USER_GET_USERS;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/users")
public class Users {
    // The Java method will process HTTP GET requests
    @GET
    // The Java method will process content identified by the MIME Media type "text/plain"
    @Produces("text/plain")
    public String getClichedMessage() {
        // Return some cliched textual content
        return "Users list:\nNo element found...";
    }


//    @PersistenceContext(name = "users")
//    private EntityManager entityManager;
    // The Java method will process HTTP POST requests
    @POST
    // The Java method will process content identified by the MIME Media type "application/json"
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    //TODO: add transfer object here
    public UserDTO persistUser(UserDTO body) {

        String persistenceUnitName = "users";
        EntityManagerFactory factory;

        try {

            factory = Persistence.createEntityManagerFactory(persistenceUnitName);
            EntityManager em = factory.createEntityManager();

            Query q = em.createNamedQuery(USER_GET_USERS);
            List<User> userList = q.getResultList();
            for (User user : userList) {
                System.out.println(user.toString());
            }
            System.out.println("Size: " + userList.size());

            em.getTransaction().begin();
            User user = new User();
            user.setFirstName(body.getFirstName());
            user.setLastName(body.getLastName());
            user.setAge(body.getAge());
            user.setProfession(body.getProfession());
            em.persist(user);
            em.getTransaction().commit();

            em.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

//        // Return Json content
//        UserDTO userDTO = null;
//        try {
//            JSONObject jsonUser = new JSONObject(body);
//            User user = new User();
//            user.setFirstName(jsonUser.get("firstName").toString());
//            user.setLastName(jsonUser.get("lastName").toString());
//            user.setAge(Integer.parseInt(jsonUser.get("age").toString()));
//            user.setProfession(jsonUser.get("profession").toString());
//
//            userDTO = convertToDto(user);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return userDTO;
        return body;
    }

    @GET
    @Produces( MediaType.APPLICATION_JSON)
    public List<UserDTO> getJsonMessage() {

        String persistenceUnitName = "users";
        EntityManagerFactory factory;

        factory = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = factory.createEntityManager();

        Query q = em.createQuery("SELECT u FROM User u");
        List<User> users = q.getResultList();
        List<UserDTO> userList = new ArrayList<UserDTO>();

        for (User user : users) {
            userList.add(convertToDto(user));
        }

        return userList;
    }

    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private UserDTO convertToDto(User user) {
        return modelMapper().map(user, UserDTO.class);
    }

    private User convertToEntity(UserDTO userDTO) {
        return modelMapper().map(userDTO, User.class);
    }
}
