package com.example.medicalregister.bean;

import java.io.Serializable;

public class ApiStringBean implements Serializable {
    private int status;
    private String data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


}
