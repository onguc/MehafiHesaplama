package com.menafi.domain;


public class MenafiTutar extends Person {

    private int id;
    private int menafiId;
    private int personId;
    private float toPaid;
    private float toBePaid; //ödenecek tutar


    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getTelNo() {
        return super.getTelNo();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMenafiId() {
        return menafiId;
    }

    public void setMenafiId(int menafiId) {
        this.menafiId = menafiId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public float getToPaid() {
        return toPaid;
    }

    public void setToPaid(float toPaid) {
        this.toPaid = toPaid;
    }

    public float getToBePaid() {
        return toBePaid;
    }

    public void setToBePaid(float toBePaid) {
        this.toBePaid = toBePaid;
    }


    public float getAvarage(){
        return toPaid+toBePaid;
    }
}
