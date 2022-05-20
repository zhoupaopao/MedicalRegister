package com.example.medicalregister.bean;


import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 产品
 *
 */
public class ItemDTO implements Serializable {
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getSize() {
		return size;
	}

	public void setSize(BigDecimal size) {
		this.size = size;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
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

	public String getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(String lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public ItemCatalogDTO getCatalog() {
		return catalog;
	}

	public void setCatalog(ItemCatalogDTO catalog) {
		this.catalog = catalog;
	}


	private Long id;

	/**
	 * 产品名称
	 */
	private String name;

	/**
	 * SKU编号
	 */
	private String number;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * 计量单位
	 */
	private String uom;

	/**
	 * 重量，单位KG
	 */
	private BigDecimal weight;

	/**
	 * 体积，单位L
	 */
	private BigDecimal size;

	/**
	 * 包装尺寸，长
	 */
	private Integer length;

	/**
	 * 包装尺寸，宽
	 */
	private Integer width;

	/**
	 * 包装尺寸，高
	 */
	private Integer height;

	/**
	 * 型号
	 */
	private String model;

	/**
	 * 规格
	 */
	private String spec;

	/**
	 * 创建时间
	 */
	private String createdAt;

	/**
	 * 创建的用户
	 */
	private String createdBy;

	/**
	 * 最后更新时间
	 */
	private String lastUpdatedAt;

	/**
	 * 最后更新的用户
	 */
	private String lastUpdatedBy;

	/**
	 * 目录
	 */
	private ItemCatalogDTO catalog;
}