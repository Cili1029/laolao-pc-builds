package com.laolao.pojo.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignInWithUsernameDTO {
    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
