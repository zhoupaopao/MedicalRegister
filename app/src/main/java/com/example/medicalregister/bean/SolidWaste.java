package com.example.medicalregister.bean;


import java.io.Serializable;

/**
 * 固废
 */
public class SolidWaste implements Serializable {
    public static final String WASTE_TYPE_GENERIC = "generic";
    public static final String WASTE_TYPE_HAZARDOUS = "hazardous";

    public static final String NUMBER_TYPE_THING = "thing";
    public static final String NUMBER_TYPE_CATEGORY = "category";

    //setter和getter
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return this.number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setUow(String uow) {
        this.uow = uow;
    }

    public String getUow() {
        return this.uow;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getUov() {
        return uov;
    }

    public void setUov(String uov) {
        this.uov = uov;
    }

    public void setPackingType(String packingType) {
        this.packingType = packingType;
    }

    public String getPackingType() {
        return this.packingType;
    }



    public String getStorageMode() {
        return storageMode;
    }

    public void setStorageMode(String storageMode) {
        this.storageMode = storageMode;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }


    //属性
    //主属性
    private Long id;
    //编号
    private String number;
    //名称
    private String name;
    //备注
    private String note;
    //类型
    private String type;
    //描述
    private String description;
    //重量
    private double weight;
    //计量单位
    private String uow;
    //体积
    private double volume;
    //体积单位
    private String uov;
    //包装类型
    private String packingType;

    //存储方式
    private String storageMode;

    //编码类型，thing一物一码，category一类一码
    private String numberType;
}