package com.example.medicalregister.bean;

import java.io.Serializable;

public class EmployeesBean implements Serializable {

    /**
     * id : 182479142388761600
     * empno : 1
     * name : 李虎（收集员）
     * sex : 1
     * telephone :
     * mobile : 18800000001
     * email :
     * photo :
     * remark :
     * department : {"id":"182176573904913408","name":"骨外科","code":1000,"type":"department","bosshead":"张三负责人","telephone":null,"remark":null,"status":null,"sequence":null,"parent":null,"createdDatetime":null,"lastUpdatedDatetime":null}
     * position : null
     * superior : null
     * account : 34cc202c50e711e8bcec1de217ccbb44
     * hiredate : null
     * enabled : true
     * activated : true
     * status : null
     * createdDatetime : null
     * lastUpdatedDatetime : null
     */

    private String id;
    private String empno;
    private String name;
    private int sex;
    private String telephone;
    private String mobile;
    private String email;
    private String photo;
    private String remark;
    private DepartmentBean department;
    private Object position;
    private Object superior;
    private String account;
    private Object hiredate;
    private boolean enabled;
    private boolean activated;
    private Object status;
    private Object createdDatetime;
    private Object lastUpdatedDatetime;
    private String idCard;


    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmpno() {
        return empno;
    }

    public void setEmpno(String empno) {
        this.empno = empno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DepartmentBean getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentBean department) {
        this.department = department;
    }

    public Object getPosition() {
        return position;
    }

    public void setPosition(Object position) {
        this.position = position;
    }

    public Object getSuperior() {
        return superior;
    }

    public void setSuperior(Object superior) {
        this.superior = superior;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Object getHiredate() {
        return hiredate;
    }

    public void setHiredate(Object hiredate) {
        this.hiredate = hiredate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Object createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Object getLastUpdatedDatetime() {
        return lastUpdatedDatetime;
    }

    public void setLastUpdatedDatetime(Object lastUpdatedDatetime) {
        this.lastUpdatedDatetime = lastUpdatedDatetime;
    }

    public static class DepartmentBean implements Serializable {
        /**
         * id : 182176573904913408
         * name : 骨外科
         * code : 1000
         * type : department
         * bosshead : 张三负责人
         * telephone : null
         * remark : null
         * status : null
         * sequence : null
         * parent : null
         * createdDatetime : null
         * lastUpdatedDatetime : null
         */

        private String id;
        private String name;
        private String code;
        private String type;
        private String bosshead;
        private Object telephone;
        private Object remark;
        private Object status;
        private Object sequence;
        private Object parent;
        private Object createdDatetime;
        private Object lastUpdatedDatetime;
//        private WasteSourcesBean.WasteSourcesLabelBean.ExtentionBean extention;
//
//        public WasteSourcesLabelBean.ExtentionBean getExtention() {
//            return extention;
//        }
//
//        public void setExtention(WasteSourcesBean.WasteSourcesLabelBean.ExtentionBean extention) {
//            this.extention = extention;
//        }

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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBosshead() {
            return bosshead;
        }

        public void setBosshead(String bosshead) {
            this.bosshead = bosshead;
        }

        public Object getTelephone() {
            return telephone;
        }

        public void setTelephone(Object telephone) {
            this.telephone = telephone;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getSequence() {
            return sequence;
        }

        public void setSequence(Object sequence) {
            this.sequence = sequence;
        }

        public Object getParent() {
            return parent;
        }

        public void setParent(Object parent) {
            this.parent = parent;
        }

        public Object getCreatedDatetime() {
            return createdDatetime;
        }

        public void setCreatedDatetime(Object createdDatetime) {
            this.createdDatetime = createdDatetime;
        }

        public Object getLastUpdatedDatetime() {
            return lastUpdatedDatetime;
        }

        public void setLastUpdatedDatetime(Object lastUpdatedDatetime) {
            this.lastUpdatedDatetime = lastUpdatedDatetime;
        }

        @Override
        public String toString() {
            return "DepartmentBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", code=" + code +
                    ", type='" + type + '\'' +
                    ", bosshead='" + bosshead + '\'' +
                    ", telephone=" + telephone +
                    ", remark=" + remark +
                    ", status=" + status +
                    ", sequence=" + sequence +
                    ", parent=" + parent +
                    ", createdDatetime=" + createdDatetime +
                    ", lastUpdatedDatetime=" + lastUpdatedDatetime +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "EmployeesBean{" +
                "id='" + id + '\'' +
                ", empno='" + empno + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", telephone='" + telephone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                ", remark='" + remark + '\'' +
                ", department=" + department +
                ", position=" + position +
                ", superior=" + superior +
                ", account='" + account + '\'' +
                ", hiredate=" + hiredate +
                ", enabled=" + enabled +
                ", activated=" + activated +
                ", status=" + status +
                ", createdDatetime=" + createdDatetime +
                ", lastUpdatedDatetime=" + lastUpdatedDatetime +
                '}';
    }
}
