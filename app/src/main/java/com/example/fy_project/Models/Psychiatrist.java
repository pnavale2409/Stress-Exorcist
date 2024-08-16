package com.example.fy_project.Models;

public class Psychiatrist {

    String Psychiatrist_name,location,fees,rating;
    int Psychiatrist_image;

    public Psychiatrist() {
    }

    public Psychiatrist(String psychiatrist_name, String location, String fees, String rating, int psychiatrist_image) {
        Psychiatrist_name = psychiatrist_name;
        this.location = location;
        this.fees = fees;
        this.rating = rating;
        Psychiatrist_image = psychiatrist_image;
    }

    public String getPsychiatrist_name() {
        return Psychiatrist_name;
    }

    public void setPsychiatrist_name(String psychiatrist_name) {
        Psychiatrist_name = psychiatrist_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getPsychiatrist_image() {
        return Psychiatrist_image;
    }

    public void setPsychiatrist_image(int psychiatrist_image) {
        Psychiatrist_image = psychiatrist_image;
    }
}
