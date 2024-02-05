package edu.uncc.assignment03;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Response implements Serializable {
   String name = "N/A", email = "N/A", role = "N/A", education = "N/A", maritalStatus = "N/A", livingStatus = "N/A", incomeStatus = "N/A";
    public Response(String name, String email, String role) {
       this.name = name;
       this.email = email;
       this.role = role;
   }

   public Response() {
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getRole() {
      return role;
   }

   public void setRole(String role) {
      this.role = role;
   }

   public String getEducation() {
      return education;
   }

   public void setEducation(String education) {
      this.education = education;
   }
   public String getMaritalStatus() {
      return maritalStatus;
   }

   public void setMaritalStatus(String maritalStatus) {
       this.maritalStatus = maritalStatus;
   }

   public String getLivingStatus() {
      return livingStatus;
   }

   public void setLivingStatus(String livingStatus) {
       this.livingStatus = livingStatus;
   }
   public String getIncomeStatus() {
      return incomeStatus;
   }

   public void setIncomeStatus(String incomeStatus) {
      this.incomeStatus = incomeStatus;
   }

   @NonNull
   @Override
   public String toString() {
      return "Response{" +
              "name='" + name + '\'' +
              ", email='" + email + '\'' +
              ", role='" + role + '\'' +
              ", education='" + education + '\'' +
              ", maritalStatus='" + maritalStatus + '\'' +
              ", livingStatus='" + livingStatus + '\'' +
              ", incomeStatus='" + incomeStatus + '\'' +
              '}';
   }
}
