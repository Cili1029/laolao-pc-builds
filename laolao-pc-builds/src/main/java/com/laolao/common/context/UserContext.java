package com.laolao.common.context;

public class UserContext {

    private static final ThreadLocal<Integer> USER = new ThreadLocal<>();

    public static void setCurrentId(Integer id) {
        USER.set(id);
    }

    public static Integer getCurrentId() {
        return USER.get();
    }

    public static void removeCurrentId() {
        USER.remove();
    }
}
