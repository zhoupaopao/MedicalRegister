package com.arch.demo.network_api.beans;

import java.io.Serializable;

public class LoginNetBean implements Serializable {
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
