package com.musala.testRest.restapi;

import com.musala.testRest.restapi.helloworld.HelloWorld;
import com.musala.testRest.restapi.projects.Project;
import com.musala.testRest.restapi.projects.Projects;
import com.musala.testRest.restapi.users.User;
import com.musala.testRest.restapi.users.Users;

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
        h.add(Projects.class);
        h.add(Project.class);
        return h;
    }
}