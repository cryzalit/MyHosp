package com.opylypiv.myhosp;

public class Doctor {
    long id;
    long idhosp;
    int point;
    double points;
    int sumpoints;
    String fullname;
    String spec;
    String codespec;
    String photoURL;


    public int getPoint() {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdhosp() {
        return idhosp;
    }

    public void setIdhosp(long idhosp) {
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

    public void setPoint(int point) {
        this.point = point;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public void hashmap() {

    }

}
