package com.example.medicalregister.tree;



import com.example.medicalregister.bean.ItemDTO;

import java.util.ArrayList;
import java.util.List;

public class Node<T, B> {

    /**
     * 传入的实体对象
     */
    public B bean;
    /**
     * 设置开启 关闭的图片
     */
    public int iconExpand = -1, iconNoExpand = -1;

    private T id;
    /**
     * 根节点pId为0
     */
    private T pId;

    private String name;
    /**
     * 物料名称
     */
    private String itemName;
    /**
     * 物料code
     */
    private String itemNumber;

    /**
     * 件数
     */
    private int quantity;


    /**
     * 当前的级别
     */
    private int level;

    /**
     * 是否展开
     */
    private boolean isExpand = true;

    private int icon = -1;

    private String type;

    /**
     * 下一级的子Node
     */
    private List<Node> children = new ArrayList<>();

    /**
     * 父Node
     */
    private Node parent;

    private String batchNumber;

//    private SerialNumberDTO serialNumberDTO;

    private ItemDTO item;

    private boolean showChecked = false;

    private String lot;

    private String weight;
    /**
     * 是否被checked选中
     */
    private boolean isChecked;
    /**
     * 是否为新添加的
     */
    public boolean isNewAdd = true;


    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public boolean isShowChecked() {
        return showChecked;
    }

    public void setShowChecked(boolean showChecked) {
        this.showChecked = showChecked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public Node() {
    }

    public Node(T id, T pId, String name, String itemName, String itemNumber, int quantity, String lot, String weight) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.itemName = itemName;
        this.itemNumber = itemNumber;
        this.quantity = quantity;
        this.lot = lot;
        this.weight = weight;
    }

    public Node(T id, T pId, String name) {
        super();
        this.id = id;
        this.pId = pId;
        this.name = name;
    }

    public Node(T id, T pId, String name, String type) {
        super();
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.type = type;
    }

    public Node(T id, T pId, String name, B bean) {
        super();
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.bean = bean;
    }

    public Node(T id, T pId, String name, String type, String weight, boolean showChecked, B bean) {
        super();
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.bean = bean;
        this.type = type;
        this.showChecked = showChecked;
        this.weight = weight;
    }

    public Node(T id, T pId, String name, String type, String weight, String lot, boolean showChecked, B bean) {
        super();
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.bean = bean;
        this.type = type;
        this.showChecked = showChecked;
        this.weight = weight;
        this.lot = lot;
    }

//    public Node(T id, T pId, int quantity, String batchNumber, SerialNumberDTO serialNumberDTO, ItemDTO item, String weight, String type) {
//        this.id = id;
//        this.pId = pId;
//        this.quantity = quantity;
//        this.batchNumber = batchNumber;
//        this.serialNumberDTO = serialNumberDTO;
//        this.item = item;
//        this.weight = weight;
//        this.type=type;
//    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public T getpId() {
        return pId;
    }

    public void setpId(T pId) {
        this.pId = pId;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

//    public SerialNumberDTO getSerialNumberDTO() {
//        return serialNumberDTO;
//    }
//
//    public void setSerialNumberDTO(SerialNumberDTO serialNumberDTO) {
//        this.serialNumberDTO = serialNumberDTO;
//    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * 是否为跟节点
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 判断父节点是否展开
     *
     * @return
     */
    public boolean isParentExpand() {
        if (parent == null)
            return false;
        return parent.isExpand();
    }

    /**
     * 是否是叶子界点
     *
     * @return
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }

    /**
     * 获取level
     */
    public int getLevel() {

        return parent == null ? 0 : parent.getLevel() + 1;
    }

    /**
     * 设置展开
     *
     * @param isExpand
     */
    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {

            for (Node node : children) {
                node.setExpand(isExpand);
            }
        }
    }

}
