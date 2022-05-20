package com.example.medicalregister.common;

public class CustomTopBean {
    private String text;
    private boolean clicked=false;


    public CustomTopBean(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }
}
