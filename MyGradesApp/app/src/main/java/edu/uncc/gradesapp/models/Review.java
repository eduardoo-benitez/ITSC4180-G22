package edu.uncc.gradesapp.models;

import com.google.firebase.Timestamp;
public class Review {
    String postText, author, course;
    Timestamp createdAt;

    public String getReview() {
        return postText;
    }

    public void setReview(String postText) {
        this.postText = postText;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "CourseReview{" +
                "postText='" + postText + '\'' +
                ", author='" + author + '\'' +
                ", course='" + course + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
