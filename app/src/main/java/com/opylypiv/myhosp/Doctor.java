package com.opylypiv.myhosp;

import android.graphics.drawable.Drawable;

public class Doctor {
    String id;
    String id_hosp;
    String fullname;
    String spec;
    String codespec;
    String photoURL;
    Double point;

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdhosp() {
        return id_hosp;
    }

    public void setIdhosp(String id_hosp) {
        this.id_hosp = id_hosp;
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

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getPhoto() {
        return photoURL;
    }

    public void String(String photoURL) {
        this.photoURL = photoURL;
    }
}
