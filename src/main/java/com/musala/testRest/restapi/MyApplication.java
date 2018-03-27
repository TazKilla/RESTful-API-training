package com.musala.testRest.restapi;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

// Defines the base URI for all resource URIs
@ApplicationPath("/")
// The Java class declares root resource and provides classes
public class MyApplication extends Application {

    // The method returns a non-empty collection with classes,
    // that must be included in the published JAX-RS application
    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add(HelloWorld.class);
        h.add(Users.class);
        h.add(User.class);
        //h.add(UserDTO.class);
        return h;
    }
}