package com.example.medicalregister.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 废物产生源
 *
 */
public class WasteSourceBean implements Serializable {


	//setter和getter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPersonInCharge() {
		return personInCharge;
	}

	public void setPersonInCharge(String personInCharge) {
		this.personInCharge = personInCharge;
	}

	public void setChildren(List<WasteSourceBean> children) {
		this.children = children;
	}

	public List<WasteSourceBean> getChildren() {
		return this.children;
	}

	public void setParent(WasteSourceBean parent) {
		this.parent = parent;
	}

	public WasteSourceBean getParent() {
		return this.parent;
	}

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	public Integer getCollType() {
		return collType;
	}

	public void setCollType(Integer collType) {
		this.collType = collType;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getEmpno() {
		return empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	//属性
	//主属性
	private Long id;

	//名称
	private String name;
	//身份证号
	private String idCard;
	//编号
	private String number;
	//工号
	private String empno;
	//负责人
	private String personInCharge;
    //产生源类型
	private String type;
	// 扩展数据
	private String extention;

	//归集方式
	private Integer collType;

	//子产生源
	private List<WasteSourceBean> children;

	//父产生源
	private WasteSourceBean parent;

	//医院名称
	private String hospitalName;


}