package com.laolao.common.context;

public class SqlLogContext {

    private static final ThreadLocal<Boolean> LOG_FLAG = ThreadLocal.withInitial(() -> false);

    /**
     * 开启 SQL 记录
     */
    public static void enable() {
        LOG_FLAG.set(true);
    }

    /**
     * 当前请求是否需要记录 SQL
     */
    public static boolean shouldLog() {
        return LOG_FLAG.get();
    }

    /**
     * 清除标记
     */
    public static void remove() {
        LOG_FLAG.remove();
    }
}
