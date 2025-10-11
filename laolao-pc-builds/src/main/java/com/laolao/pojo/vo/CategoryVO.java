package com.laolao.pojo.vo;


public class CategoryVO {
    /**
     * 主键
     */
    private int id;

    /**
     * 商品类型
     */
    private int type;

    /**
     * 商品名
     */
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryVO(int id, int type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public CategoryVO() {
    }

    @Override
    public String toString() {
        return "CategoryVO{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
