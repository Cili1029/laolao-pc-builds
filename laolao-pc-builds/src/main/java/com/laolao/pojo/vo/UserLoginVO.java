package com.laolao.pojo.vo;

import java.io.Serializable;

public class UserLoginVO implements Serializable {
    private Long id;
    private String username;
    private String name;
    private String jwt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public UserLoginVO(Long id, String username, String name, String jwt) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.jwt = jwt;
    }

    public UserLoginVO() {
    }
}
