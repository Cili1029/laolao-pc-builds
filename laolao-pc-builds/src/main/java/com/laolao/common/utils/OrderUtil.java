package com.laolao.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class OrderUtil {

    /**
     * 生成订单号
     * 格式：ORD + 时间戳 + 随机字符串
     */
    public static String generateOrderNumber() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = UUID.randomUUID().toString().substring(0, 3).toUpperCase();
        return "ORD" + timestamp + random;
    }
}
