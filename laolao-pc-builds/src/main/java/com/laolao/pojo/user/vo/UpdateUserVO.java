package com.laolao.pojo.user.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UpdateUserVO {
    /**
     * 用户Id
     */
    private int id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String name;

}
