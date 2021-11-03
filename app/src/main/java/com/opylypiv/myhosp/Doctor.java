package com.opylypiv.myhosp;

public class Doctor {
    int id;
    int idhosp;
    float point;
    double points;
    int sumpoints;
    String fullname;
    String spec;
    String codespec;
    String photoURL;


    public float getPoint() {
        return point;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public long getSumpoints() {
        return sumpoints;
    }

    public void setSumpoints(int sumpoints) {
        this.sumpoints = sumpoints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdhosp() {
        return idhosp;
    }

    public void setIdhosp(int idhosp) {
        this.idhosp = idhosp;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getCodespec() {
        return codespec;
    }

    public void setCodespec(String codespec) {
        this.codespec = codespec;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Doctor getDoctorByID(int id) {
        if (this.getId() == id) {
            return this;
        }
        return null;

    }
}
