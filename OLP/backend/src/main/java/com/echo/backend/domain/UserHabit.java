package com.echo.backend.domain;

public class UserHabit {

    private int uid;

    private String term;

    private int termHot;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getTermHot() {
        return termHot;
    }

    public void setTermHot(int termHot) {
        this.termHot = termHot;
    }
}
