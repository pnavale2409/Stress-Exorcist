package com.example.fy_project.Models;

public class Conversation {
    String message,id,sender_id;
    Long time;

    public Conversation() {
    }

    public Conversation(String message, String id, String sender_id) {
        this.message = message;
        this.id = id;
        this.sender_id = sender_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
