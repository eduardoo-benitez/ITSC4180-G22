package edu.uncc.gradesapp.models;

import java.util.List;

public class CourseReview {
    String course;
    Long numReviews;
    List<String> favoredBy;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Long getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(Long numReviews) {
        this.numReviews = numReviews;
    }

    public List<String> getFavoredBy() {
        return favoredBy;
    }

    public void setFavoredBy(List<String> favoredBy) {
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
