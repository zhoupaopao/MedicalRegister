package com.example.medicalregister.bean;


import java.io.Serializable;
import java.util.List;

/**
 * 固废存量
 *
 */
public class WasteInventoryBean implements Serializable {


    //setter和getter
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return this.source;
    }

    public void setWaste(SolidWaste waste) {
        this.waste = waste;
    }

    public SolidWaste getWaste() {
        return this.waste;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getLot() {
        return this.lot;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getWeight() {
        return this.weight;
    }

    public void setUow(String uow) {
        this.uow = uow;
    }

    public String getUow() {
        return this.uow;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getVolume() {
        return this.volume;
    }

    public void setUov(String uov) {
        this.uov = uov;
    }

    public String getUov() {
        return this.uov;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }




    //属性
    //主属性
    private Long id;

    //产生源
    private String source;

    //固废
    private SolidWaste waste;

    //批次
    private String lot;

    //数量
    private Integer quantity;

    //重量
    private Double weight;

    //重量单位
    private String uow;

    //体积
    private Double volume;

    //体积单位
    private String uov;

    //创建时间
    private String createdAt;



}
