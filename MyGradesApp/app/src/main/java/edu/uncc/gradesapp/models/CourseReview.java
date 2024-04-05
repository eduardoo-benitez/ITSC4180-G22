package edu.uncc.gradesapp.models;

import java.util.ArrayList;

public class CourseReview {
    String course, docId;
    int numReviews;
    ArrayList<String> favoredBy;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public int getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(int numReviews) {
        this.numReviews = numReviews;
    }

    public ArrayList<String> getFavoredBy() {
        return favoredBy;
    }

    public void setFavoredBy(ArrayList<String> favoredBy) {
        this.favoredBy = favoredBy;
    }

    @Override
    public String toString() {
        return "CourseReview{" +
                "course='" + course + '\'' +
                ", numReviews='" + numReviews + '\'' +
                ", favoredBy=" + favoredBy +
                '}';
    }
}
