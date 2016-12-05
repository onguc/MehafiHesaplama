package com.menafi.domain;


public class Person {
    private int id;
    private String name;
    private String telNo;
    private boolean deleted;
    private long recordTimestamp;

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

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public long getRecordTimestamp() {
        return recordTimestamp;
    }

    public void setRecordTimestamp(long recordTimestamp) {
        this.recordTimestamp = recordTimestamp;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", telNo=" + telNo +
                ", status=" + deleted +
                ", recordTimestamp=" + recordTimestamp +
                '}';
    }
}
