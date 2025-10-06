package com.laolao.pojo.vo;

import java.math.BigDecimal;

public class CategoryVO {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryVO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryVO() {}

    @Override
    public String toString() {
        return "CategoryVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" +
                '}';
    }
}
