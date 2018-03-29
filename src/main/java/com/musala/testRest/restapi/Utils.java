package com.musala.testRest.restapi;

import com.musala.testRest.restapi.projects.Project;
import com.musala.testRest.restapi.projects.ProjectDTO;
import com.musala.testRest.restapi.users.User;
import com.musala.testRest.restapi.users.UserDTO;
import org.modelmapper.ModelMapper;

public class Utils {

    // Useful methods, User to UserDTO conversion

    private static Utils instance = null;
    protected Utils() {

    }

    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    private ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public Object convertToDto(Object object) {
        if (object instanceof User) {
            return modelMapper().map(object, UserDTO.class);
        } else if (object instanceof Project) {
            return modelMapper().map(object, ProjectDTO.class);
        } else {
            return null;
        }
    }

    public Object convertToEntity(Object object) {
        if (object instanceof UserDTO) {
            return modelMapper().map(object, User.class);
        } else if (object instanceof ProjectDTO) {
            return modelMapper().map(object, Project.class);
        } else {
            return null;
        }
    }
}
