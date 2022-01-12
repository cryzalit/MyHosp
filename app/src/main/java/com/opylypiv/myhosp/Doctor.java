package com.opylypiv.myhosp;

public class Doctor {
    int id;
    int idhosp;
    double point;
    double points;
    double sumpoints;
    String fullname;
    String spec;
    String codespec;
    String photoURL;
    String doctorUID;


    public double getPoint() {
        point = Float.parseFloat((sumpoints / points) + "");
        return point;
    }

    public String getDoctorUID() {
        return doctorUID;
    }

    public void setDoctorUID(String doctorUID) {
        this.doctorUID = doctorUID;
    }

    public Doctor getDoctorByID(int id) {
        if (this.getId() == id) {
            return this;
        }
        return null;

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

    public void setPoint(double point) {
        this.point = point;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public double getSumpoints() {
        return sumpoints;
    }

    public void setSumpoints(double sumpoints) {
        this.sumpoints = sumpoints;
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

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
