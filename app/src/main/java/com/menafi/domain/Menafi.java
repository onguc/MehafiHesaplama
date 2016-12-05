package com.menafi.domain;


public class Menafi {
    private int id;
    private String name;
    long timestampRecordDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimestampRecordDate() {
        return timestampRecordDate;
    }

    public void setTimestampRecordDate(long timestampRecordDate) {
        this.timestampRecordDate = timestampRecordDate;
    }
}
