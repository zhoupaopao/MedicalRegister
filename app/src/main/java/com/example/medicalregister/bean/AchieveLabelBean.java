package com.example.medicalregister.bean;

/**
 * Created by Lenovo on 2019/7/26.
 */

public class AchieveLabelBean {
    private String number;
    private String type;
    private String object;
    private String status;
    private String lot;
    private String tags;
    private Organization organization;
    private SkuBeanMsg data;
    private String marks;
    public AchieveLabelBean(String type, String object, String status, String lot, String tags, Organization organization, SkuBeanMsg data){
        this.type=type;
        this.object=object;
        this.status=status;
        this.lot=lot;
        this.tags=tags;
        this.organization=organization;
        this.data=data;
    }
    public AchieveLabelBean(String type, String object, String status, String lot, String tags, Organization organization, SkuBeanMsg data, String marks){
        this.type=type;
        this.object=object;
        this.status=status;
        this.lot=lot;
        this.tags=tags;
        this.organization=organization;
        this.data=data;
        this.marks=marks;
    }
    public static class Organization{
        private String openid;

        public Organization(String openid){
            this.openid=openid;
        }
    }
    public static class SkuBeanMsg {
        private String id;
        private String number;
        private String name;
        private String type;
        private String code;
        private String department;
        private String organization;
        private String weight;
        private String transactor;//交接人
        private String transactorIdCard;//交接人身份证
        private String collector;//收集人

        public SkuBeanMsg(String id, String number, String name, String type, String code, String department, String organization){
            this.id=id;
            this.number=number;
            this.name=name;
            this.type=type;
            this.code=code;
            this.department=department;
            this.organization=organization;
        }
        public SkuBeanMsg(String id, String number, String name, String type, String code, String department, String organization, String weight, String transactor, String collector){
            this.id=id;
            this.number=number;
            this.name=name;
            this.type=type;
            this.code=code;
            this.department=department;
            this.organization=organization;
            this.weight=weight;
            this.transactor=transactor;//交接人
            this.collector=collector;//收集人
        }
        public SkuBeanMsg(String id, String number, String name, String type, String code, String department, String organization, String weight, String transactor, String transactorIdCard, String collector){
            this.id=id;
            this.number=number;
            this.name=name;
            this.type=type;
            this.code=code;
            this.department=department;
            this.organization=organization;
            this.weight=weight;
            this.transactor=transactor;//交接人
            this.collector=collector;//收集人
            this.transactorIdCard=transactorIdCard;
        }
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
