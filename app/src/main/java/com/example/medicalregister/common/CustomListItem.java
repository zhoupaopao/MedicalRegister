package com.example.medicalregister.common;

public class CustomListItem {
    private String id;
    private String number;
    private String name;
    private String title;
    private String message;
    private int visible=1;
    private boolean checked=false;

    public CustomListItem(String id, String number, String name, String title, String message) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.title = title;
        this.message = message;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

//    public int getVisible() {
//        return visible;
//    }
//
//    public void setVisible(int visible) {
//        this.visible = visible;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
