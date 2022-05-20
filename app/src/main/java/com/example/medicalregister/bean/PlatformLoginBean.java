package com.example.medicalregister.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zcx on 2019/3/2.
 */

public class PlatformLoginBean implements Serializable {

    /**
     * roles : [{"id":2,"name":"医废收集员","type":"WASTE_COLLECTOR","description":"医废收集员","buildin":true,"status":"1"}]
     * status : 200
     */

    private int status;
    private List<RolesBean> roles;
    private OrganizationBean organization;
    private EmployeeBean employee;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<RolesBean> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesBean> roles) {
        this.roles = roles;
    }

    public OrganizationBean getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationBean organization) {

        this.organization = organization;
    }

    public EmployeeBean getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeBean employee) {
        this.employee = employee;
    }

    public static class EmployeeBean implements Serializable {
        private String idCard;

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }
    }

    public static class OrganizationBean implements Serializable {
        private String description;
        private String name;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public static class RolesBean implements Serializable {
        /**
         * id : 2
         * name : 医废收集员
         * type : WASTE_COLLECTOR
         * description : 医废收集员
         * buildin : true
         * status : 1
         */

        private long id;
        private String name;
        private String type;
        private String description;
        private boolean buildin;
        private String status;

        public long getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isBuildin() {
            return buildin;
        }

        public void setBuildin(boolean buildin) {
            this.buildin = buildin;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
