package com.laolao.common.context;

public class SqlLogContext {

    // 为空则为不记录
    private static final ThreadLocal<String> CONTENT = new ThreadLocal<>();

    /**
     * 开启 SQL 记录(有描述则为开启)
     */
    public static void setBusinessName(String businessName) {
        CONTENT.set(businessName);
    }

    /**
     * 当前请求是否需要记录 SQL
     */
    public static String getBusinessName() {
        return CONTENT.get();
    }

    /**
     * 清除标记
     */
    public static void remove() {
        CONTENT.remove();
    }
}
