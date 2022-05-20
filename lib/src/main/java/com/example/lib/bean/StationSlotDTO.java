package com.example.lib.bean;


import java.io.Serializable;

/**
 * 槽位
 *
 */
public class StationSlotDTO implements Serializable {


	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber() {
		return this.number;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getRow() {
		return this.row;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	public Integer getColumn() {
		return this.column;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}

	public Integer getLayer() {
		return this.layer;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getState() {
		return this.state;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public String getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	/**
	 * 主属性
	 */
	private Long id;

	/**
	 * 槽位编号
	 */
	private String number;

	/**
	 * 优先级
	 */
	private Integer priority;

	/**
	 * 行
	 */
	private Integer row;

	/**
	 * 列
	 */
	private Integer column;

	/**
	 * 层
	 */
	private Integer layer;

	/**
	 * 状态0-可用的；1-已锁定；2-有故障
	 */
	private Integer state;

	/**
	 * 是否启用
	 */
	private Boolean enabled;

	/**
	 * 创建时间
	 */
	private String createdAt;

	/**
	 * 创建用户
	 */
	private String createdBy;

	/**
	 * 工作台编号
	 */
	private String stationNumber;

	private String containerNumber;//当前在槽位上的料箱

}