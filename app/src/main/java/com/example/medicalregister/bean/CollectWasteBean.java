package com.example.medicalregister.bean;

import java.io.Serializable;

public class CollectWasteBean implements Serializable {
    private int quantity;
    private double weight;
    private String uow;
    private CollectWaste waste;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getWom() {
        return uow;
    }

    public void setWom(String wom) {
        this.uow = wom;
    }

    public CollectWaste getWaste() {
        return waste;
    }

    public void setWaste(CollectWaste waste) {
        this.waste = waste;
    }
}
