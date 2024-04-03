package edu.uncc.gradesapp.models;

import com.google.firebase.Timestamp;
public class Review {
    String postText, createdByName, course, createdByUId, docId;
    Timestamp createdAt;

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCreatedByUId() {
        return createdByUId;
    }

    public void setCreatedByUId(String createdByUId) {
        this.createdByUId = createdByUId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Review{" +
                "postText='" + postText + '\'' +
                ", createdByName='" + createdByName + '\'' +
                ", course='" + course + '\'' +
                ", createdByUId='" + createdByUId + '\'' +
                ", docId='" + docId + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
