package edu.uncc.assignment04;

import java.io.Serializable;

public class Response implements Serializable {

    String name;
    String email;
    String role;
    String education;
    String maritalStatus;
    String livingStatus;
    String incomeStatus;

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
    public String getMaritalStatus() {
        return maritalStatus;
    }
    public String getLivingStatus() {
        return livingStatus;
    }
    public String getIncomeStatus() {
        return incomeStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setLivingStatus(String livingStatus) {
        this.livingStatus = livingStatus;
    }

    public void setIncomeStatus(String incomeStatus) {
        this.incomeStatus = incomeStatus;
    }
}
