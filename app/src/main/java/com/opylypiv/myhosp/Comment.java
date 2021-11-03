package com.opylypiv.myhosp;

public class Comment {
    int id;
    String text;
    String point;
    int iduser;
    int iddoctor;
    int idhosp;
    boolean isAnswer;
    String answer;


    public String getAnswer() {
        return answer;
    }

    public boolean getisAnswer() {
        return isAnswer;
    }

    public void setisAnswer(boolean ansver) {
        isAnswer = ansver;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
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
