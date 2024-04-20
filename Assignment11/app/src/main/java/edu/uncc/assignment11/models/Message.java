package edu.uncc.assignment11.models;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class Message {
    String docId, otherDocId, title, body, sender, recipient;
    Timestamp sentAt;
    boolean read = false;
    ArrayList<String> titleArr;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getOtherDocId() {
        return otherDocId;
    }

    public void setOtherDocId(String otherDocId) {
        this.otherDocId = otherDocId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Timestamp getSentAt() {
        return sentAt;
    }

    public void setSentAt(Timestamp sentAt) {
        this.sentAt = sentAt;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public ArrayList<String> getTitleArr() {
        return titleArr;
    }

    public void setTitleArr(ArrayList<String> titleArr) {
        this.titleArr = titleArr;
    }

    @Override
    public String toString() {
        return "Message{" +
                "docId='" + docId + '\'' +
                ", otherDocId='" + otherDocId + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", sentAt=" + sentAt +
                ", read=" + read +
                ", titleArr=" + titleArr +
                '}';
    }
}
