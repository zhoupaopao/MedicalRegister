package com.example.lib.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 工作站
 * @author Kevin
 * @date 2020/9/29 10:13
 */
public class WorkstationDTO implements Serializable {
    /**
     * 工作站编号
     */
    private Long id;

    /**
     * 工作站名称
     */
    private String name;

    /**
     * 代码
     */
    private String code;

    /**
     * 工作站类型
     */
    private String type;

    /**
     * 工作模式，独占的exclusive、并行的parallel
     */
    private String workingMode;

    /**
     * 工作站备注
     */
    private String note;

    /**
     * 是否被锁定
     */
    private Boolean locked;

    /**
     * 工作站代码
     */
    private String number;

    /**
     * 槽位
     */
    private List<StationSlotDTO> slots;

    /**
     * 创建时间
     */
    private String createdAt;

    /**
     * 创建用户
     */
    private String createdBy;

    /**
     * 工作站位置，坐标
     */
    private String location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWorkingMode() {
        return workingMode;
    }

    public void setWorkingMode(String workingMode) {
        this.workingMode = workingMode;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public List<StationSlotDTO> getSlots() {
        return slots;
    }

    public void setSlots(List<StationSlotDTO> slots) {
        this.slots = slots;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}