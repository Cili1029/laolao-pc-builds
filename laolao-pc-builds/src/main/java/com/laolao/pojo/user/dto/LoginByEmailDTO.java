package com.laolao.pojo.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginByEmailDTO {
    /**
     * 邮箱
     */
    private String email;

    /**
     * 邮箱验证码
     */
    private String emailCode;
}
