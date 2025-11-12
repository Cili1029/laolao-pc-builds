package com.laolao.pojo.forum.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ImageVO {
    /**
     * 图片数量
     */
    private int count;

    /**
     * 结果字符串
     */
    private List<String> images;
}
