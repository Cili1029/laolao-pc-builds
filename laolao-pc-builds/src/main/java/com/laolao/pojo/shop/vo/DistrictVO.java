package com.laolao.pojo.shop.vo;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
}
