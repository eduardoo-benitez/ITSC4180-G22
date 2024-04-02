package edu.uncc.gradesapp.models;

public class Grade {
    public String letterGrade, courseName, courseNumber, semester, creditHours, createdByUId, docId;

    public Grade() {}

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseNumber;
    }

    public void setCourseCode(String courseCode) {
        this.courseNumber = courseCode;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(String creditHours) {
        this.creditHours = creditHours;
    }

    public String getCreatedByUId() {
        return createdByUId;
    }

    public void setCreatedByUId(String createdByUId) {
        this.createdByUId = createdByUId;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "letterGrade='" + letterGrade + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseNumber='" + courseNumber + '\'' +
                ", semester='" + semester + '\'' +
                ", creditHours='" + creditHours + '\'' +
                ", createdByUId='" + createdByUId + '\'' +
                ", docId='" + docId + '\'' +
                '}';
    }
}