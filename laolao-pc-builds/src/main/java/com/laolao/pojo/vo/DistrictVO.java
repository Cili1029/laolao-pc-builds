package com.laolao.pojo.vo;

import java.io.Serializable;

public class DistrictVO implements Serializable {
    /**
     * 行政区划级别
     * country:国家
     * province:省份（直辖市会在province显示）
     * city:市（直辖市会在province显示）
     * district:区县
     * street:街道
     */
    private String level;

    /**
     * 区域编码
     */
    private int adcode;

    /**
     * 行政区名称
     */
    private String name;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getAdcode() {
        return adcode;
    }

    public void setAdcode(int adcode) {
        this.adcode = adcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DistrictVO() {
    }

    public DistrictVO(String level, int adcode, String name) {
        this.level = level;
        this.adcode = adcode;
        this.name = name;
    }

    @Override
    public String toString() {
        return "DistrictVO{" +
                "level='" + level + '\'' +
                ", adcode=" + adcode +
                ", name='" + name + '\'' +
                '}';
    }
}
