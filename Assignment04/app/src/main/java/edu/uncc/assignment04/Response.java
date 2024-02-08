package edu.uncc.assignment04;

import java.io.Serializable;

public class Response implements Serializable {

    String name;
    String email;
    String role;
    String education;

    public Response(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getRole() {
        return role;
    }
    public String getEducation() {
        return education;
    }
}
