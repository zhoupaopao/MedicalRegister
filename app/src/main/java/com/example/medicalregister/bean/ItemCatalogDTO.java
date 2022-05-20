package com.example.medicalregister.bean;



import java.io.Serializable;

/**
 * 物料目录
 * @author Kevin
 * @date 2020/12/11 22:06
 */
public class ItemCatalogDTO implements Serializable {
    private Long id;

    private String name;

    private String description;

    private Integer sequence;

    private String path;

    private Boolean disabled;

    private ItemCatalogDTO parent;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public ItemCatalogDTO getParent() {
        return parent;
    }

    public void setParent(ItemCatalogDTO parent) {
        this.parent = parent;
    }
}
