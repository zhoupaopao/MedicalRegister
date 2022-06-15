package com.example.medicalregister.bean;

import java.util.Map;

public class MqttBean {
    private String method;
    private Long ID;
    private String productKey;
    private String deviceCode;//设备编号
    private Map<String, String>param;
    private Map<String, String>param1;
    private Map<String, String>param2;


    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }

    public Map<String, String> getParam1() {
        return param1;
    }

    public void setParam1(Map<String, String> param1) {
        this.param1 = param1;
    }

    public Map<String, String> getParam2() {
        return param2;
    }

    public void setParam2(Map<String, String> param2) {
        this.param2 = param2;
    }
}
