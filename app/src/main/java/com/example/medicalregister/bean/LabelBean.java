package com.example.medicalregister.bean;

import java.io.Serializable;


/**
 * 标签
 *
 */
public class LabelBean implements Serializable {
    //已创建
	public final static String STATUS_CREATED = "created";
	//已打印
	public final static String STATUS_PRINTED = "printed";
	//已发放
    public final static String STATUS_DISTRIBUTED = "distributed";
    //已使用
    public final static String STATUS_USED = "used";
    //已作废
    public final static String STATUS_INVALIDATED = "invalidated";

    //类型，一物一码
    public final static String TYPE_THING = "thing";
    //类型，一类一码
    public final static String TYPE_CATEGORY = "category";
    //类型，一批一码
    public final static String TYPE_BATCH = "batch";

    //setter和getter
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

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public void setData(ListBean data) {
		this.data = data;
	}

	public ListBean getData() {
		return this.data;
	}

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	//属性
	//主属性
	private Long id;

	//标签号
	private String number;

	//类型
	private String type;

	//标识对象
	private String object;

	//状态
	private String status;

	//申请批次
    private String lot;
	//扩展数据
	private ListBean data;

	//标记
    private String tags;

	//所属组织
    private Organization organization;

	//创建时间
	private String createdAt;

	//特殊标记医废列表
	private String marks;
	public static class ListBean implements Serializable{
		/**
		 * id : 183350917305730048
		 * type : hazardous
		 * number : 1111111
		 * name : 废油
		 * note : null
		 * description : 111
		 * uom : T
		 * organization=无锡第一人民医院
		 * department=骨外科
		 * code=831-001-01
		 */

		private String id;
		private String type;
		private String number;
		private String name;
		private Object note;
		private String description;
		private String uow;
		private String collector;
		private String transactor;
		private String organization;
		private String department;
		private String code;
		private String weight;
		private String transactorIdCard;


		public String getWeight() {
			return weight;
		}

		public void setWeight(String weight) {
			this.weight = weight;
		}

		public String getTransactor() {
			return transactor;
		}

		public void setTransactor(String transactor) {
			this.transactor = transactor;
		}

		public String getCollector() {
			return collector;
		}

		public void setCollector(String collector) {
			this.collector = collector;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Object getNote() {
			return note;
		}

		public void setNote(Object note) {
			this.note = note;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getUom() {
			return uow;
		}

		public void setUom(String uom) {
			this.uow = uom;
		}

		public String getOrganization() {
			return organization;
		}

		public void setOrganization(String organization) {
			this.organization = organization;
		}

		public String getDepartment() {
			return department;
		}

		public void setDepartment(String department) {
			this.department = department;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getTransactorIdCard() {
			return transactorIdCard;
		}

		public void setTransactorIdCard(String transactorIdCard) {
			this.transactorIdCard = transactorIdCard;
		}

		public ListBean(){

		}
		public ListBean(String id,String number,String name){
			this.id=id;
			this.number=number;
			this.name=name;
		}
	}
}