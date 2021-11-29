package com.opylypiv.myhosp;

public class Comment {
    long id;
    String text;
    String point;
    int iduser;
    int iddoctor;
    int idhosp;
    boolean answer;
    String textanswer;

    public boolean isAnswer() {
        return answer;
    }

    public void setisAnswer(boolean isanswer) {
        answer = isanswer;
    }

    public String getTextanswer() {
        return textanswer;
    }

    public void setTextanswer(String textanswer) {
        this.textanswer = textanswer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getIddoctor() {
        return iddoctor;
    }

    public void setIddoctor(int iddoctor) {
        this.iddoctor = iddoctor;
    }

    public int getIdhosp() {
        return idhosp;
    }

    public void setIdhosp(int idhosp) {
        this.idhosp = idhosp;
    }

}
