package com.example.medicalregister.bean;

/**
 * Created by Lenovo on 2019/7/27.
 */

public class AcheveReturnLabelBean {
    private String status;
    private labelBean label;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public labelBean getLabel() {
        return label;
    }

    public void setLabel(labelBean label) {
        this.label = label;
    }

    public class labelBean{
        private String number;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }


    }
}
