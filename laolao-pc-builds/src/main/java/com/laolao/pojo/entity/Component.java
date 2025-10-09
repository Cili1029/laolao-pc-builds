package com.laolao.pojo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Component implements Serializable {

    private int id;

    private String name;

    private int categoryId;

    private String image;

    private String commonDescription;

    private Integer status;

    private int sort;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private String createBy;

    private String updateBy;

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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCommonDescription() {
        return commonDescription;
    }

    public void setCommonDescription(String commonDescription) {
        this.commonDescription = commonDescription;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Component(int id, String name, int categoryId, String image, String commonDescription, Integer status, int sort, LocalDateTime createAt, LocalDateTime updateAt, String createBy, String updateBy) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.image = image;
        this.commonDescription = commonDescription;
        this.status = status;
        this.sort = sort;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createBy = createBy;
        this.updateBy = updateBy;
    }

    public Component() {
    }

    @Override
    public String toString() {
        return "Component{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", image='" + image + '\'' +
                ", commonDescription='" + commonDescription + '\'' +
                ", status=" + status +
                ", sort=" + sort +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                '}';
    }
}
