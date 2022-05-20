package com.example.lib.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhaochengxiao on 2017/11/28.
 */

public class UserBean implements Serializable {


    /**
     * nickname : 收集员
     * email : sj@test.com
     * mobile : 18800000001
     * avatar : 
     * location : null
     * sex : null
     * birthday : null
     * timezone : null
     * locale : 
     * status : 2
     * tenants : [{"openid":"4f5e867c50d411e8bcec1de217ccbb44","name":"无锡某某高科技集团有限公司","status":1,"primary":true,"createdAt":"2018-05-20"}]
     */

    private String nickname;
    private String email;
    private String mobile;
    private String avatar;
    private String location;
    private String sex;
    private String birthday;
    private String timezone;
    private String locale;
    private int status;
    private List<TenantsBean> tenants;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<TenantsBean> getTenants() {
        return tenants;
    }

    public void setTenants(List<TenantsBean> tenants) {
        this.tenants = tenants;
    }

    public static class TenantsBean implements Serializable {
        @Override
        public String toString() {
            return "TenantsBean{" +
                    "openid='" + openid + '\'' +
                    ", name='" + name + '\'' +
                    ", status=" + status +
                    ", primary=" + primary +
                    ", createdAt='" + createdAt + '\'' +
                    '}';
        }

        /**
         * openid : 4f5e867c50d411e8bcec1de217ccbb44
         * name : 无锡某某高科技集团有限公司
         * status : 1
         * primary : true
         * createdAt : 2018-05-20
         */

        private String openid;
        private String name;
        private int status;
        private boolean primary;
        private String createdAt;

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public boolean isPrimary() {
            return primary;
        }

        public void setPrimary(boolean primary) {
            this.primary = primary;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", avatar='" + avatar + '\'' +
                ", location='" + location + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", timezone='" + timezone + '\'' +
                ", locale='" + locale + '\'' +
                ", status=" + status +
                ", tenants=" + tenants +
                '}';
    }
}
