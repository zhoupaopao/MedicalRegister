package com.example.medicalregister.bean;

/**
 * Created by Lenovo on 2019/7/19.
 */

import java.io.Serializable;
import java.util.List;

/**
 * 选中的登记列表
 */
public class SelectedRegistBean implements Serializable {
//    {
//        "waste": {
//        "id": "183350917305730001"
//    },
//        "source": "",
//            "quantity": "",
//            "wom": "",
//            "weight": "",
//            "createdAt": "",
//            "createdBy": "",
//            "details": [
//        {
//            "label": "S0000000001",
//                "quantity": 2,
//                "uom": "PCS"
//        }
//    ]
//    }
private String source;
    private double weight;
    private String uow;
    private int quantity;
    private long createdAt;
//    private String createdBy;
    private weasteBean waste;
    private List<ChildRegistBean> details;
    private String lot;


    private int maxQuantity=999;//默认999

    public static class weasteBean implements Serializable {
        private String id;
        private String name;
        private String number;
        private double weight;

        public weasteBean(String id, String name, String number){
            this.id=id;
            this.number=number;
            this.name=name;
        }
        public weasteBean(String id, String name, String number, double weight){
            this.id=id;
            this.number=number;
            this.name=name;
            this.weight=weight;
        }
//        protected weasteBean(Parcel in) {
//            id = in.readString();
//            name = in.readString();
//        }
//
//        public static final Creator<weasteBean> CREATOR = new Creator<weasteBean>() {
//            @Override
//            public weasteBean createFromParcel(Parcel in) {
//                return new weasteBean(in);
//            }
//
//            @Override
//            public weasteBean[] newArray(int size) {
//                return new weasteBean[size];
//            }
//        };


        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
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

//        public String getNumber() {
//            return number;
//        }
//
//        public void setNumber(String number) {
//            this.number = number;
//        }
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        @Override
//        public void writeToParcel(Parcel dest, int flags) {
//            dest.writeString(id);
//            dest.writeString(name);
//        }
    }

    public static class ChildRegistBean implements Serializable {
        private String label;
        private String uow;
        private double weight;
        private String source;
        private String containerNumber;
        private String marks;
        public ChildRegistBean(){
        }
        public ChildRegistBean(String label, String uom, double quantity){
            this.label=label;
            this.uow=uom;
            this.weight=quantity;
        }
        public ChildRegistBean(String label, String uom, double quantity, boolean isRegister){
            this.label=label;
            this.uow=uom;
            this.weight=quantity;
        }
        public ChildRegistBean(String label, String uom, double quantity, boolean isRegister, String marks){
            this.label=label;
            this.uow=uom;
            this.weight=quantity;
            this.marks=marks;
        }
        public ChildRegistBean(String label, String uom, double quantity, boolean isRegister, boolean ischecked){
            this.label=label;
            this.uow=uom;
            this.weight=quantity;
        }
        public ChildRegistBean(String label, String uom, double quantity, boolean isRegister, String source, String containerNumber){
            this.label=label;
            this.uow=uom;
            this.weight=quantity;
            this.source=source;
            this.containerNumber=containerNumber;
        }
        public ChildRegistBean(String label, String uom, double quantity, boolean isRegister, boolean ischecked, boolean canchecked){
            this.label=label;
            this.uow=uom;
            this.weight=quantity;
        }
        public ChildRegistBean(String label, String uom, double quantity, boolean isRegister, boolean ischecked, boolean canchecked, String source){
            this.label=label;
            this.uow=uom;
            this.weight=quantity;
            this.source=source;
        }

        public String getContainerNumber() {
            return containerNumber;
        }

        public void setContainerNumber(String containerNumber) {
            this.containerNumber = containerNumber;
        }

        public String getMarks() {
            return marks;
        }

        public void setMarks(String marks) {
            this.marks = marks;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }


        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getUom() {
            return uow;
        }

        public void setUom(String uom) {
            this.uow = uom;
        }

        public double getQuantity() {
            return weight;
        }

        public void setQuantity(double quantity) {
            this.weight = quantity;
        }
    }
    public SelectedRegistBean(){

    }
    public SelectedRegistBean(String source, double weight, String wom, int quantity, long createdAt, String createdBy, weasteBean waste, List<ChildRegistBean> details, String lot) {
        this.source = source;
        this.weight = weight;
        this.uow = wom;
        this.quantity = quantity;
        this.createdAt = createdAt;
//        this.createdBy = createdBy;
        this.waste = waste;
        this.details=details;
        this.lot=lot;

    }
    public SelectedRegistBean(String source, double weight, String wom, int quantity, long createdAt, String createdBy, weasteBean waste, List<ChildRegistBean> details, String lot, boolean allCheck) {
        this.source = source;
        this.weight = weight;
        this.uow = wom;
        this.quantity = quantity;
        this.createdAt = createdAt;
//        this.createdBy = createdBy;
        this.waste = waste;
        this.details=details;
        this.lot=lot;

    }
    //为一类一码极大值显示用
    public SelectedRegistBean(String source, double weight, String wom, int quantity, long createdAt, String createdBy, weasteBean waste, List<ChildRegistBean> details, String lot, boolean allCheck, int maxQuantity) {
        this.source = source;
        this.weight = weight;
        this.uow = wom;
        this.quantity = quantity;
        this.createdAt = createdAt;
//        this.createdBy = createdBy;
        this.waste = waste;
        this.details=details;
        this.lot=lot;
        this.maxQuantity=maxQuantity;
    }
    public SelectedRegistBean(String source, double weight, String wom, int quantity, long createdAt, String createdBy, weasteBean waste, List<ChildRegistBean> details) {
        this.source = source;
        this.weight = weight;
        this.uow = wom;
        this.quantity = quantity;
        this.createdAt = createdAt;
//        this.createdBy = createdBy;
        this.waste = waste;
        this.details=details;

    }
    public SelectedRegistBean(String source, double weight, String wom, int quantity, long createdAt, weasteBean waste, List<ChildRegistBean> details, String lot) {
        this.source = source;
        this.weight = weight;
        this.uow = wom;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.waste = waste;
        this.details=details;
        this.lot=lot;

    }
    public SelectedRegistBean(String source, double weight, String wom, int quantity, long createdAt, String createdBy, weasteBean waste, List<ChildRegistBean> details, String lot, int maxQuantity) {
        this.source = source;
        this.weight = weight;
        this.uow = wom;
        this.quantity = quantity;
        this.createdAt = createdAt;
//        this.createdBy = createdBy;
        this.waste = waste;
        this.details=details;
        this.lot=lot;
        this.maxQuantity=maxQuantity;

    }
//    protected SelectedRegistBean(Parcel in) {
//        source = in.readString();
//        weight = in.readDouble();
//        wom = in.readString();
//        quantity = in.readInt();
//        createdAt = in.readString();
//        createdBy = in.readString();
//        waste = in.readParcelable(weasteBean.class.getClassLoader());
//        details = in.readArrayList(ChildRegistBean.class.getClassLoader());
//    }
//
//    public static final Creator<SelectedRegistBean> CREATOR = new Creator<SelectedRegistBean>() {
//        @Override
//        public SelectedRegistBean createFromParcel(Parcel source) {
//            SelectedRegistBean bean=new SelectedRegistBean();
//
//            bean.source=source.readString();
//            bean.weight=source.readDouble();
//            bean.wom=source.readString();
//            bean.quantity=source.readInt();
//            bean.createdAt=source.readString();
//            bean.createdBy=source.readString();
//            bean.waste = source.readParcelable(weasteBean.class.getClassLoader()); // 这个地方的ClassLoader不能为null
//            bean.details = source.readArrayList(ChildRegistBean.class.getClassLoader()); // 这个地方的ClassLoader不能为null
//            return bean;
//        }
//
//        @Override
//        public SelectedRegistBean[] newArray(int size) {
//            return new SelectedRegistBean[size];
//        }
//    };


    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }



    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

//    public String getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(String createdBy) {
//        this.createdBy = createdBy;
//    }

    public weasteBean getWaste() {
        return waste;
    }

    public void setWaste(weasteBean waste) {
        this.waste = waste;
    }

    public List<ChildRegistBean> getDetails() {
        return details;
    }

    public void setDetails(List<ChildRegistBean> details) {
        this.details = details;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }



    //
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(source);
//        dest.writeDouble(weight);
//        dest.writeString(wom);
//        dest.writeInt(quantity);
//        dest.writeString(createdAt);
//        dest.writeString(createdBy);
//        dest.writeParcelable(waste, flags);
//        dest.writeList(details);
//    }


}
