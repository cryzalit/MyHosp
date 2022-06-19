package com.opylypiv.myhosp;

public class Hospital {
    int id;
    String nameorganization;
    double latitude;
    double longtitude;
    String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameorganization() {
        return nameorganization;
    }

    public void setNameorganization(String nameorganization) {
        this.nameorganization = nameorganization;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}