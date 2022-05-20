package com.example.medicalregister.common;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class CustomItemBean implements MultiItemEntity {
    /**
     * 单选，针对是否
     */
    public final static int SWITCH = 0;
    /**
     * 多个选择项的单选
     */
    public final static int SWITCHMORE = 3;
    /**
     * 输入框
     */
    public final static int EDIT = 2;
    /**
     * 多选
     */
    public final static int TEXT = 1;
    /**
     * 标题
     */
    public final static int TITLE = 4;
    /**
     * 时间
     */
    public final static int TIME = 5;
    /**
     * 时间
     */
    public final static int TEXTMORE = 6;

    /**
     * 点击后作出更改
     */
    public final static  int CHANGE=7;

    /**
     * 列表
     */
    public final static  int LISTVIEW=8;

    /**
     * 是否必填
     */
    private boolean mast = false;

    private int type;
    private String id;
    private String name;
    private String value;
    /**
     * 输入框拓展
     * 1 整数限制
     * 2 double限制
     * 3 电话限制
     * (此处可定义更多的类型等，然后在adapter中做对应的操作)
     */
    private int editExp;

    private boolean isVisible=true;//是否可见

    private String editHint;

    private List<CustomItemBean> lists;



    public CustomItemBean(int type, String id, String name, String value) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.value = value;
    }
    public CustomItemBean(int type, String id, String name, String value, boolean isVisible) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.value = value;
        this.isVisible=isVisible;
    }


    public CustomItemBean(int type, String id, String name, String value, int editExp) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.value = value;
        this.editExp = editExp;
    }


    public CustomItemBean(int type, String id, String name, String value, int editExp, String editHint) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.value = value;
        this.editExp = editExp;
        this.editHint=editHint;
    }





    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }



    public String getEditHint() {
        return editHint;
    }

    public void setEditHint(String editHint) {
        this.editHint = editHint;
    }

    public boolean isMast() {
        return mast;
    }

    public void setMast(boolean mast) {
        this.mast = mast;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getEditExp() {
        return editExp;
    }

    public void setEditExp(int editExp) {
        this.editExp = editExp;
    }



    public List<CustomItemBean> getLists() {
        return lists;
    }

    public void setLists(List<CustomItemBean> lists) {
        this.lists = lists;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
